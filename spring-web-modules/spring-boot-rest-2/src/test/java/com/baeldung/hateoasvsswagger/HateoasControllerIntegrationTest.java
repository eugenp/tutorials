package com.baeldung.hateoasvsswagger;

import com.baeldung.hateoasvsswagger.model.NewUser;
import com.baeldung.hateoasvsswagger.model.User;
import com.baeldung.hateoasvsswagger.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserHateoasController.class)
@AutoConfigureMockMvc
class HateoasControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userService;

    @Test
    void whenAllUsersRequested_thenReturnAllUsersWithLinks() throws Exception {
        User user1 = new User(1, "John Doe", "john.doe@example.com", LocalDateTime.now());
        User user2 = new User(2, "Jane Smith", "jane.smith@example.com", LocalDateTime.now());

        when(userService.getAllUsers()).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/api/hateoas/users").accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$._embedded.userList[0].id").value(1))
          .andExpect(jsonPath("$._embedded.userList[0].name").value("John Doe"))
          .andExpect(jsonPath("$._embedded.userList[0]._links.self.href").exists())
          .andExpect(jsonPath("$._embedded.userList[1].id").value(2))
          .andExpect(jsonPath("$._embedded.userList[1].name").value("Jane Smith"))
          .andExpect(jsonPath("$._links.self.href").exists());
    }

    @Test
    void whenUserByIdRequested_thenReturnUserByIdWithLinks() throws Exception {
        User user = new User(1, "John Doe", "john.doe@example.com", LocalDateTime.now());

        when(userService.getUserById(1)).thenReturn(user);

        mockMvc.perform(get("/api/hateoas/users/1").accept(MediaType.APPLICATION_JSON))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.id").value(1))
          .andExpect(jsonPath("$.name").value("John Doe"))
          .andExpect(jsonPath("$.email").value("john.doe@example.com"))
          .andExpect(jsonPath("$._links.self.href").exists())
          .andExpect(jsonPath("$._links.all-users.href").exists());
    }

    @Test
    void whenUserCreationRequested_thenReturnUserByIdWithLinks() throws Exception {
        User user = new User(1, "John Doe", "john.doe@example.com", LocalDateTime.now());
        when(userService.createUser(any(NewUser.class))).thenReturn(user);

        mockMvc.perform(post("/api/hateoas/users").contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"John Doe\",\"email\":\"john.doe@example.com\"}"))
          .andExpect(status().isCreated())
          .andExpect(jsonPath("$.id").value(1))
          .andExpect(jsonPath("$.name").value("John Doe"))
          .andExpect(jsonPath("$._links.self.href").exists());
    }
}
