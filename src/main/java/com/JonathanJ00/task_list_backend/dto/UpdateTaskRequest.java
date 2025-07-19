package com.JonathanJ00.task_list_backend.dto;

import com.JonathanJ00.task_list_backend.entity.TaskStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateTaskRequest {

    @NotNull
    private TaskStatus status;

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
