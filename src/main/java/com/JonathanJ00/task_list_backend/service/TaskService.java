package com.JonathanJ00.task_list_backend.service;

import com.JonathanJ00.task_list_backend.dto.SaveTaskRequest;
import com.JonathanJ00.task_list_backend.entity.Task;
import com.JonathanJ00.task_list_backend.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void saveTask(SaveTaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDate());

        taskRepository.save(task);
    }

    public Task getTaskById(Long id) throws NoSuchElementException {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if (taskOptional.isEmpty()) {
            throw new NoSuchElementException("No task found with id " + id);
        }

        return taskOptional.get();
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
}
