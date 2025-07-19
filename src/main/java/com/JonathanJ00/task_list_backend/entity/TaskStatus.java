package com.JonathanJ00.task_list_backend.entity;

public enum TaskStatus {
    CREATED("Created"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    public final String status;

    private TaskStatus(String status) {
        this.status = status;
    }

}
