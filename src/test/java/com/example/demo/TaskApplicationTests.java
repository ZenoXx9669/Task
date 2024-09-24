package com.example.demo;

import com.example.demo.model.Task;
import com.example.demo.model.User;
import com.example.demo.repositories.TaskRepository;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.TaskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@SpringBootTest
class TaskApplicationTests {
	@Autowired
	private TaskService taskService;
	@Autowired
	private TaskRepository taskRepository;
	@Autowired
	private UserRepository userRepository;
	@Test
	void contextLoads() {
	}

	@Test
	void createTest(){
		Task task = new Task();
		task.setName("Reverse Integer");
		task.setDescription("Given a signed 32-bit integer x, return x with its digits reversed.");
		task.setPasswordForTest("testMQ");
		taskService.createTask(task,3L);
	}
	@Test
	void getTasksUserTest(){
		List<Task> tasks = taskService.getUserTasks(3L);
		List<Task> initialTasks = taskRepository.findByUserId(3L);
		Assertions.assertEquals(tasks.size(),initialTasks.size());
		for (int i = 0; i < tasks.size(); i++) {
			Assertions.assertEquals(tasks.get(i).getName(),initialTasks.get(i).getName());
			Assertions.assertEquals(tasks.get(i).getDescription(),initialTasks.get(i).getDescription());
			Assertions.assertEquals(tasks.get(i).isCompleted(),initialTasks.get(i).isCompleted());
		}
	}
	@Test
	void updateTaskTest(){
		Task task = new Task();
		task.setId(3L);
		task.setName("Two sum");
		task.setDescription("Test to successfully");
		task.setPasswordForTest("testMQ");
		taskService.updateTask(task,3L);
	}
	@Test
	void deleteTaskTest(){
		Long id = 4L;
		taskService.deleteTask(id);
	}
}
