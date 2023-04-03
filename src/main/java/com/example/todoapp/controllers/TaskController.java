package com.example.todoapp.controllers;

import com.example.todoapp.models.Task;
import com.example.todoapp.repository.TaskRepository;
import com.example.todoapp.service.TaskService;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
public class TaskController {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskService taskService;

    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Internal server error")})

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

//    @PostMapping("")
//    public ResponseEntity<Task> createTask(@RequestBody Task task) {
//        task.setCreatedTime(LocalDateTime.now());
//        Task createdTask = taskService.saveTask(task);
//
//        // Tạo URI đến tài nguyên mới được tạo
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
//                .path("/{id}")
//                .buildAndExpand(createdTask.getId())
//                .toUri();
//
//        // Trả về Response với status code 201 và URI đến tài nguyên mới
//        return ResponseEntity.created(uri).body(createdTask);
//    }
@PostMapping("")
@ResponseStatus(HttpStatus.CREATED)
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
