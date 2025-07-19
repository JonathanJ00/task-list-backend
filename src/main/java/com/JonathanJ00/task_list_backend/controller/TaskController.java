package com.JonathanJ00.task_list_backend.controller;

import com.JonathanJ00.task_list_backend.dto.GetTaskResponse;
import com.JonathanJ00.task_list_backend.dto.SaveTaskRequest;
import com.JonathanJ00.task_list_backend.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.NoSuchElementException;

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
}
