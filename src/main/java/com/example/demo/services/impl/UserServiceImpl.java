package com.example.demo.services.impl;

import com.example.demo.code.CodeForConfirm;
import com.example.demo.exceptions.EmailExistException;
import com.example.demo.exceptions.PasswordsNotMatchException;
import com.example.demo.model.Permission;
import com.example.demo.model.User;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.UserService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final JavaMailSender javaMailSender;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private CodeForConfirm globalConfirm;
    private User globalUser;
    @Override
    public void register(User user) {
        if (!userRepository.existsByEmail(user.getEmail())) {
            if (user.getPassword().equals(user.getRePassword())) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                List<Permission> roles = new ArrayList<>();
                Permission role = new Permission();
                role.setId(1L);
                roles.add(role);
                user.setPermissions(roles);


                CodeForConfirm confirmCode = new CodeForConfirm();
                sendVerificationEmail(user.getEmail(), String.valueOf(confirmCode.getCode()));
                globalConfirm = confirmCode;
                globalUser = user;
            } else {
                throw new PasswordsNotMatchException("Passwords not match, please repeat!");
            }
        } else {
            throw new EmailExistException("Email exists!");
        }
    }

    @Override
    public User checkCode(short code) {
        User user = globalUser;
        CodeForConfirm checkConfirm = globalConfirm;
        if (compareToCode(checkConfirm, code)) {
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    public User login(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            return (User) authentication.getPrincipal();
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Неверный email или пароль");
        }
    }



    private boolean compareToCode(CodeForConfirm confirm, short equalsCode) {
        return confirm.getCode() == equalsCode;
    }

    private void sendVerificationEmail(String to, String code) {
        String subject = "Ваш код подтверждения от " + "Название Компании";
        String text = "Спасибо, что выбрали наш университет. Ваш код подтверждения: " + code;

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(text);

            javaMailSender.send(message);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
