package es._7.todolist.controllers;

import es._7.todolist.configtests.WithMockMyUser;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContext;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.fasterxml.jackson.databind.ObjectMapper;

import es._7.todolist.configuration.JwtAuthFilter;
import es._7.todolist.models.AppUser;
import es._7.todolist.models.Task;
import es._7.todolist.services.AppUserService;
import es._7.todolist.services.TaskService;
import es._7.todolist.services.JwtUtilService;

import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.List;

@WebMvcTest(TaskController.class)
@AutoConfigureMockMvc(addFilters = false)
class TaskControllerTest {
    
    @Autowired
    private MockMvc mvc;


    @MockBean
    private TaskService taskService;

    @MockBean
    private JwtAuthFilter jwtAuthFilter;

    @MockBean
    private GetTokenController getTokenController;

    @MockBean
    private JwtUtilService jwtUtilService;

    @MockBean
    private SecurityContext securityContext;

    @MockBean
    private AppUserService appUserService;

    @Test
    @WithMockMyUser(username = "sub")
    void whenSaveTask_ReturnSavedTask() throws Exception {
        AppUser user = new AppUser();
        user.setSubId("sub");

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task 1");
        task.setDescription("Description 1");
        task.setCompletion_status("Completed");
        task.setPriority("High");
        task.setUser(user);
        
        // Mock behaviors for userService and taskService
        when(appUserService.findBySubId("sub")).thenReturn(user);
        when(taskService.save(Mockito.any(Task.class))).thenReturn(task);

        // Perform the test request
        mvc.perform(post("/api/tasks")
            .contentType(MediaType.APPLICATION_JSON)
            .content(new ObjectMapper().writeValueAsString(task)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.title", is("Task 1")))
            .andExpect(jsonPath("$.description", is("Description 1")))
            .andExpect(jsonPath("$.completion_status", is("Completed")))
            .andExpect(jsonPath("$.priority", is("High")));
    }

    @Test
    @WithMockMyUser(username = "sub")
    void deleteTak_ReturnNoContent() throws Exception {
        AppUser user = new AppUser();
        user.setSubId("sub");

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task 1");
        task.setDescription("Description 1");
        task.setCompletion_status("Completed");
        task.setPriority("High");
        task.setUser(user);

        when(taskService.findById(1L)).thenReturn(task);

        doNothing().when(taskService).delete(1L);

        // Perform the test request
        mvc.perform(delete("/api/tasks/{id}", 1L)
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());
    }

    @Test
    @WithMockMyUser(username = "sub")
    void testDeleteTask_ReturnNotAllowed() throws Exception{
        AppUser user = new AppUser();
        user.setSubId("sub1");

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task 1");
        task.setDescription("Description 1");
        task.setCompletion_status("Completed");
        task.setPriority("High");
        task.setUser(user);

        when(taskService.findById(1L)).thenReturn(task);

        doNothing().when(taskService).delete(1L);

        // Perform the test request
        mvc.perform(delete("/api/tasks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockMyUser(username = "sub")
    void testDeleteTask_ReturnNotFound() throws Exception{
        AppUser user = new AppUser();
        user.setSubId("sub");

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task 1");
        task.setDescription("Description 1");
        task.setCompletion_status("Completed");
        task.setPriority("High");
        task.setUser(user);

        when(taskService.findById(1L)).thenReturn(null);

        doNothing().when(taskService).delete(1L);

        // Perform the test request
        mvc.perform(delete("/api/tasks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockMyUser(username = "sub")
    void testUpdateTask_ReturnNotFound() throws Exception{
        AppUser user = new AppUser();
        user.setSubId("sub");

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task 1");
        task.setDescription("Description 1");
        task.setCompletion_status("Completed");
        task.setPriority("High");
        task.setUser(user);

        when(taskService.findById(1L)).thenReturn(null);

        // Perform the test request
        mvc.perform(put("/api/tasks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(task)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockMyUser(username = "sub")
    void testUpdateTask_ReturnForbidden() throws Exception{
        AppUser user = new AppUser();
        user.setSubId("sub1");

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task 1");
        task.setDescription("Description 1");
        task.setCompletion_status("Completed");
        task.setPriority("High");
        task.setUser(user);

        when(taskService.findById(1L)).thenReturn(task);

        // Perform the test request
        mvc.perform(put("/api/tasks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(task)))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockMyUser(username = "sub")
    @Disabled
    void testUpdateTask_ReturnUpdatedTask() throws Exception{
        AppUser user = new AppUser();
        user.setSubId("sub");

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task 1");
        task.setDescription("Description 1");
        task.setCompletion_status("In progression");
        task.setPriority("Low");
        task.setUser(user);

        Task task1 = new Task();
        task1.setId(1L);
        task1.setTitle("Task 1 update");
        task1.setDescription("Description 1 update");
        task1.setCompletion_status("Completed");
        task1.setPriority("High");
        task1.setUser(user);


        when(taskService.findById(1L)).thenReturn(task);
        when(taskService.save(Mockito.any(Task.class))).thenReturn(task);

        // Perform the test request
        mvc.perform(put("/api/tasks/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(task1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Task 1 update ")))
                .andExpect(jsonPath("$.description", is("Description 1 update")))
                .andExpect(jsonPath("$.completion_status", is("Completed")))
                .andExpect(jsonPath("$.priority", is("High")));
    }

    @Test
    @WithMockMyUser(username = "sub")
    void whenGetTasksByUserandPriority() throws Exception{
        AppUser user = new AppUser();
        user.setSubId("sub");

        Task task = new Task();
        task.setId(1L);
        task.setTitle("Task 1");
        task.setDescription("Description 1");
        task.setCompletion_status("Completed");
        task.setPriority("High");
        task.setUser(user);

        Task task1 = new Task();
        task1.setId(2L);
        task1.setTitle("Task 2");
        task1.setDescription("Description 2");
        task1.setCompletion_status("In progression");
        task1.setPriority("Low");
        task1.setUser(user);

        Task task2 = new Task();
        task2.setId(3L);
        task2.setTitle("Task 3");
        task2.setDescription("Description 3");
        task2.setCompletion_status("In progression");
        task2.setPriority("High");
        task2.setUser(user);

        when(appUserService.findBySubId("sub")).thenReturn(user);
        when(taskService.findByUserAndPriorityTasks(user, "High")).thenReturn(List.of(task, task2));

        // Perform the test request
        mvc.perform(get("/api/tasks/priority/{priority}", "High")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Task 1")))
                .andExpect(jsonPath("$[0].description", is("Description 1")))
                .andExpect(jsonPath("$[0].completion_status", is("Completed")))
                .andExpect(jsonPath("$[0].priority", is("High")))
                .andExpect(jsonPath("$[1].title", is("Task 3")))
                .andExpect(jsonPath("$[1].description", is("Description 3")))
                .andExpect(jsonPath("$[1].completion_status", is("In progression")))
                .andExpect(jsonPath("$[1].priority", is("High")));
    }





}
