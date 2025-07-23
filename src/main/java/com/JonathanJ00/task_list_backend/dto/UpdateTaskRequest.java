package com.JonathanJ00.task_list_backend.dto;

import com.JonathanJ00.task_list_backend.entity.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request object containing information about the task to be updated.")
public class UpdateTaskRequest {

    @NotNull
    @Schema(description = "The new status of the task to be updated")
    private TaskStatus status;

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
