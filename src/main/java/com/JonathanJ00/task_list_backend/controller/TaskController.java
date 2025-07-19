package com.JonathanJ00.task_list_backend.controller;

import com.JonathanJ00.task_list_backend.dto.SaveTaskRequest;
import com.JonathanJ00.task_list_backend.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
}
