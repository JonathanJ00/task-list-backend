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
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
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

    private final String entityTitle = "Task";
    private final String entityDescription = "Description";
    private final TaskStatus entityStatus = TaskStatus.IN_PROGRESS;
    private final LocalDateTime entityDueDate = LocalDateTime.of(2020, 1, 1, 0, 0);
    private long entityId;
    private Task task = new Task();

    private final String requestTitle = "Test Title";
    private final String requestDescription = "Test Description";
    private final TaskStatus requestStatus = TaskStatus.CREATED;
    private final LocalDateTime requestDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
    private SaveTaskRequest request = new SaveTaskRequest();

    @BeforeEach
    public void setUp() {
        task.setTitle(entityTitle);
        task.setDescription(entityDescription);
        task.setStatus(entityStatus);
        task.setDueDate(entityDueDate);

        repository.deleteAll();
        Task savedTask = repository.save(task);
        entityId = savedTask.getId();
        repository.flush();

        request.setTitle(requestTitle);
        request.setDescription(requestDescription);
        request.setStatus(requestStatus);
        request.setDate(requestDate);
    }

    @Test
    public void testSaveTask_happyPath() throws Exception {
        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());

        List<Task> tasks = repository.findAll();
        assertEquals(2, tasks.size(), "New task should have been created");

        Task savedTask = tasks.get(1);
        assertEquals(requestTitle, savedTask.getTitle(), "Title saved incorrectly");
        assertEquals(requestDescription, savedTask.getDescription(), "Description saved incorrectly");
        assertEquals(requestStatus, savedTask.getStatus(), "Status saved incorrectly");
        assertEquals(requestDate, savedTask.getDueDate(), "Due date saved incorrectly");
    }

    @Test
    public void testSaveTask_noTitle_shouldReturnBadRequest() throws Exception {
        request.setTitle(null);

        mockMvc.perform(post("/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

        List<Task> tasks = repository.findAll();
        assertEquals(1, tasks.size(), "No new task should have been created");
    }

    @Test
    public void testGetTask_happyPath() throws Exception {
        mockMvc.perform(get("/" + entityId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(entityId))
                .andExpect(jsonPath("title").value(entityTitle))
                .andExpect(jsonPath("description").value(entityDescription))
                .andExpect(jsonPath("status").value(entityStatus.toString()))
                .andExpect(jsonPath("date").value(entityDueDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));
    }

    @Test
    public void testGetTask_invalidId_shouldReturnNotFound() throws Exception {
        mockMvc.perform(get("/" + entityId + 1))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetAllTasks_happyPath() throws Exception {
        mockMvc.perform(get("/tasks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0].id").value(entityId))
                .andExpect(jsonPath("[0].title").value(entityTitle))
                .andExpect(jsonPath("[0].description").value(entityDescription))
                .andExpect(jsonPath("[0].status").value(entityStatus.toString()))
                .andExpect(jsonPath("[0].date").value(entityDueDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))));

    }
}
