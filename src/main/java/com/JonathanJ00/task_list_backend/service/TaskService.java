package com.JonathanJ00.task_list_backend.service;

import com.JonathanJ00.task_list_backend.dto.SaveTaskRequest;
import com.JonathanJ00.task_list_backend.dto.UpdateTaskRequest;
import com.JonathanJ00.task_list_backend.entity.Task;
import com.JonathanJ00.task_list_backend.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

/**
 * Service class containing methods to perform logic needed by the application.
 */
@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    /**
     * Transforms request into a Task object and saves this task
     *
     * @param request Object of type SaveTaskRequest
     */
    public void saveTask(SaveTaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(request.getStatus());
        task.setDueDate(request.getDate());

        taskRepository.save(task);
    }

    /**
     * Retrieves a Task with the provided ID.
     *
     * @param id ID of Task to be retrieved
     * @return Task object
     * @throws NoSuchElementException If no Task with an ID of id exists.
     */
    public Task getTaskById(Long id) throws NoSuchElementException {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if (taskOptional.isEmpty()) {
            throw new NoSuchElementException("No task found with id " + id);
        }

        return taskOptional.get();
    }

    /**
     * Retrieves details of all existing tasks.
     *
     * @return List of all existing tasks.
     */
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    /**
     * Updates and returns the details of a specific task.
     *
     * @param id      ID of the task to be updated.
     * @param request UpdateTaskRequest object with the new details of the task.
     * @return Task with updated details.
     * @throws NoSuchElementException If no Task exists with the provided ID.
     */
    public Task updateTask(long id, UpdateTaskRequest request) throws NoSuchElementException {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if (taskOptional.isEmpty()) {
            throw new NoSuchElementException("No task found with id " + id);
        }

        Task task = taskOptional.get();
        task.setStatus(request.getStatus());

        return taskRepository.save(task);
    }

    /**
     * Deletes a Task with the provided ID.
     *
     * @param id ID of task to be deleted.
     * @throws NoSuchElementException If no Task exists with the provided ID.
     */
    public void deleteTask(long id) throws NoSuchElementException {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if (taskOptional.isEmpty()) {
            throw new NoSuchElementException("No task found with id " + id);
        }

        taskRepository.delete(taskOptional.get());
    }
}
