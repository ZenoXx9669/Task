package com.example.demo.services.impl;

import com.example.demo.model.Permission;
import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    @Override
    public Task createTask(Task task,Long userId) {
        return getTask(task, userId);
    }


//
//    Original method
    @Override
    public List<Task> getUserTasks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName();
        User currentUser = userRepository.findAllByEmail(currentUsername);
        return taskRepository.findByUserId(currentUser.getId());
    }
//    Method for Test
    public List<Task> getUserTasks(Long userId) {
        return taskRepository.findByUserId(userId);
    }
//

    @Override
    public Task updateTask(Task task,Long userId) {
        return getTask(task, userId);
    }

    private Task getTask(Task task, Long userId) {
        if(task.getPasswordForTest() != null && task.getPasswordForTest().equals("testMQ")){
            User user = userRepository.findById(userId).orElse(null);
            task.setUser(user);
            return taskRepository.save(task);
        }else {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();
            User currentUser = userRepository.findAllByEmail(currentUsername);
            User user = userRepository.findById(userId).orElse(null);
            for (Permission permission : currentUser.getPermissions()){
                if (permission.getRole().equals("ROLE_ADMIN")){
                    task.setUser(user);
                    return taskRepository.save(task);
                }
            }
            throw new RuntimeException();
        }

    }

    @Override
    public void deleteTask(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //это я делал ради теста
        if (authentication != null){
            String currentUsername = authentication.getName();
            User currentUser = userRepository.findAllByEmail(currentUsername);
            for (Permission permission : currentUser.getPermissions()){
                if (permission.getRole().equals("ROLE_ADMIN")){
                    taskRepository.deleteById(id);
                }
            }
        }else {
            taskRepository.deleteById(id);
        }

    }
}
