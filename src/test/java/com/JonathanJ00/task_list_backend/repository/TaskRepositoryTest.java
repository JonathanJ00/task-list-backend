package com.JonathanJ00.task_list_backend.repository;

import com.JonathanJ00.task_list_backend.entity.Task;
import com.JonathanJ00.task_list_backend.entity.TaskStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void testSave_savesNewTask() {
        Task task = new Task();
        task.setTitle("Task Title");
        task.setDescription("Task Description");
        task.setStatus(TaskStatus.CREATED);
        task.setDueDate(LocalDate.of(2028, Month.JANUARY, 18).atStartOfDay());

        taskRepository.save(task);
        List<Task> tasks = taskRepository.findAll();

        assertEquals(1, tasks.size());

        Task savedTask = tasks.getFirst();
        assertEquals(task.getTitle(), savedTask.getTitle(), "Task title not saved correctly");
        assertEquals(task.getDescription(), savedTask.getDescription(), "Task description not saved correctly");
        assertEquals(task.getStatus(), savedTask.getStatus(), "Task status not saved correctly");
        assertEquals(task.getDueDate(), savedTask.getDueDate(), "Task due date not savedCorrectly");
    }

}
