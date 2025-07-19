package com.JonathanJ00.task_list_backend.controller;

import com.JonathanJ00.task_list_backend.dto.SaveTaskRequest;
import com.JonathanJ00.task_list_backend.entity.Task;
import com.JonathanJ00.task_list_backend.entity.TaskStatus;
import com.JonathanJ00.task_list_backend.repository.TaskRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class TaskControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    private final String title = "Test Title";
    private final String description = "Test Description";
    private final TaskStatus status = TaskStatus.CREATED;
    private final LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    private SaveTaskRequest request = new SaveTaskRequest();

    @BeforeEach
    public void setUp() {
        repository.deleteAll();
        repository.flush();

        request.setTitle(title);
        request.setDescription(description);
        request.setStatus(status);
        request.setDate(date);
    }

    @Test
    public void testSaveTask_happyPath() throws Exception {
        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        List<Task> tasks = repository.findAll();
        assertEquals(1, tasks.size(), "There should be only one task");

        Task savedTask = tasks.getFirst();
        assertEquals(title, savedTask.getTitle(), "Title saved incorrectly");
        assertEquals(description, savedTask.getDescription(), "Description saved incorrectly");
        assertEquals(status, savedTask.getStatus(), "Status saved incorrectly");
        assertEquals(date, savedTask.getDueDate(), "Due date saved incorrectly");
    }

    @Test
    public void testSaveTask_noTitle_shouldReturnBadRequest() throws Exception {
        request.setTitle(null);

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        List<Task> tasks = repository.findAll();
        assertEquals(0, tasks.size(), "No task should exist");
    }
}
