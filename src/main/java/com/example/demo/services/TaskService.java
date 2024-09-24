package com.example.demo.services;

import com.example.demo.model.Task;

import java.util.List;

public interface TaskService {
    Task createTask(Task task,Long userId);
    List<Task> getUserTasks();
    Task updateTask(Task task,Long userId);
    void deleteTask(Long id);
//    Method for test
    List<Task> getUserTasks(Long userId);
}
