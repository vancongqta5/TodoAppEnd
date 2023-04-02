package com.example.todoapp.controllers;

import com.example.todoapp.models.Task;
import com.example.todoapp.repository.TaskRepository;
import com.example.todoapp.service.TaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskService taskService;
    @GetMapping("")
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }
    @GetMapping("name/{name}")
    public List<Task> getTasksByName(@PathVariable String name) {
        return taskService.getTaskByName(name);
    }
    @GetMapping("id/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @PostMapping("")
    public Task createTask(@RequestBody Task task) {
        // Set current date and time as created time
        task.setCreatedTime(LocalDateTime.now());
        return taskService.saveTask(task);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable("id") Long id, @RequestBody Task task) {
        Task existingTask = taskService.getTaskById(id);
        BeanUtils.copyProperties(task, existingTask, "id");
        return taskService.saveTask(existingTask);
    }

    @DeleteMapping("/{id}")
    public void deleteTask(@PathVariable Long id) {
        taskService.deleteTaskById(id);
    }

}
