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


@WebMvcTest(AppUserController.class)
@AutoConfigureMockMvc(addFilters = false)
class AppUserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private AppUserService userService;

    @Test
    @Disabled
    void saveAppUser() throws Exception {
        AppUser user = new AppUser();
        user.setSubId("sub");
        user.setEmail("email");
        user.setUsername("name");
        user.setTasks(List.of(new Task()));
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(user);

        when(userService.save(user)).thenReturn(user);
        mvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.subId", is("sub")))
                .andExpect(jsonPath("$.email", is("email")))
                .andExpect(jsonPath("$.name", is("name")));
    }

}
