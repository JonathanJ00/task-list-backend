package com.JonathanJ00.task_list_backend.controller;

import com.JonathanJ00.task_list_backend.dto.SaveTaskRequest;
import com.JonathanJ00.task_list_backend.dto.TaskResponse;
import com.JonathanJ00.task_list_backend.dto.UpdateTaskRequest;
import com.JonathanJ00.task_list_backend.entity.Task;
import com.JonathanJ00.task_list_backend.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestController
@Schema(description = "This endpoint contains methods to create, retrieve, update and delete tasks within the application")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Creates and saves a task with the required details.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Task was created"),
            @ApiResponse(responseCode = "400", description = "Invalid details provided.")
    })
    public void saveTask(
            @RequestBody @Valid SaveTaskRequest request) {
        taskService.saveTask(request);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Returns the details of a specific task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the task"),
            @ApiResponse(responseCode = "404", description = "Requested task was not found")
    })
    public ResponseEntity<TaskResponse> getTask(
            @Parameter(description = "The ID of the task to retrieve") @PathVariable long id) {
        try {
            return ResponseEntity.ok(new TaskResponse(taskService.getTaskById(id)));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/tasks")
    @Operation(summary = "Returns details of all saved tasks")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all tasks"),
    })
    public List<TaskResponse> getAllTasks() {
        return taskService.getAllTasks().stream().map(TaskResponse::new).collect(Collectors.toList());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Updates a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully updated the task"),
            @ApiResponse(responseCode = "404", description = "Task was not found using provided ID"),
            @ApiResponse(responseCode = "400", description = "Provided task details were invalid")
    })
    public ResponseEntity<TaskResponse> updateTask(
            @Parameter(description = "The ID of the task to update") @PathVariable long id,
            @RequestBody @Valid UpdateTaskRequest request) {
        try {
            Task updatedTask = taskService.updateTask(id, request);
            return ResponseEntity.ok(new TaskResponse(updatedTask));
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Deletes a task")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully deleted the task"),
            @ApiResponse(responseCode = "404", description = "Task was not found using provided ID"),
    })
    public void deleteTask(
            @Parameter(description = "The ID of the task to delete") @PathVariable long id) {
        try {
            taskService.deleteTask(id);
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }
}
