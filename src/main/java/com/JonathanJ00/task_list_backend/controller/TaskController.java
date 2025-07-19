package com.JonathanJ00.task_list_backend.controller;

import com.JonathanJ00.task_list_backend.dto.GetTaskResponse;
import com.JonathanJ00.task_list_backend.dto.SaveTaskRequest;
import com.JonathanJ00.task_list_backend.dto.UpdateTaskRequest;
import com.JonathanJ00.task_list_backend.entity.Task;
import com.JonathanJ00.task_list_backend.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void saveTask(@RequestBody @Valid SaveTaskRequest request) {
        taskService.saveTask(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTaskResponse> getTask(@PathVariable long id) {
        try {
            return ResponseEntity.ok(new GetTaskResponse(taskService.getTaskById(id)));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/tasks")
    public List<GetTaskResponse> getAllTasks() {
        return taskService.getAllTasks().stream().map(GetTaskResponse::new).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    public ResponseEntity<GetTaskResponse> updateTask(@PathVariable long id, @RequestBody @Valid UpdateTaskRequest request) {
        try {
            Task updatedTask = taskService.updateTask(id, request);
            return ResponseEntity.ok(new GetTaskResponse(updatedTask));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
