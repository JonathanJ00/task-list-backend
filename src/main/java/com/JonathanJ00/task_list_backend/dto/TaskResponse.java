package com.JonathanJ00.task_list_backend.dto;

import com.JonathanJ00.task_list_backend.entity.Task;
import com.JonathanJ00.task_list_backend.entity.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Request object containing information about the task returned to the client.")
public class TaskResponse {
    @Schema(description = "The ID for this task")
    private long id;

    @Schema(description = "The name of this task")
    private String title;

    @Schema(description = "The description of this task")
    private String description;

    @Schema(description = "The current status of this task")
    private TaskStatus status;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Schema(description = "The date this task is due in format yyyy-MM-dd HH:mm")
    private LocalDateTime date;

    public TaskResponse(Task task) {
        this.id = task.getId();
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.date = task.getDueDate();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
