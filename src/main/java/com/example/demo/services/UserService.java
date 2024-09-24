package com.example.demo.services;

import com.example.demo.model.User;

public interface UserService {
    void register(User user);
    User checkCode(short code);
    User login(String email, String password);
}
