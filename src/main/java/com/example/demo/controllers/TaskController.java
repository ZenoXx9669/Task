package com.example.demo.controllers;

import com.example.demo.model.Task;
import com.example.demo.services.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired
    private TaskService taskService;

    @PostMapping("/create")
    public Task createTask(@RequestBody Task task,@RequestParam Long userId){
        return taskService.createTask(task,userId);
    }

    @GetMapping
    public List<Task> getUserTasks(){
        return taskService.getUserTasks();
    }

    @PutMapping("/update")
    public Task updateTask(@RequestBody Task task,@RequestParam Long userId){
        return taskService.updateTask(task,userId);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteTask(@PathVariable("id") Long id){
        taskService.deleteTask(id);
    }
}
