package com.baeldung.springdatawebsupport.application.test;

import com.baeldung.springdatawebsupport.application.controllers.UserController;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIntegrationTest {

    @Autowired
    private UserController userController;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenUserControllerInjected_thenNotNull() throws Exception {
        assertThat(userController).isNotNull();
    }

    @Test
    public void whenGetRequestToUsersEndPointWithIdPathVariable_thenCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users/{id}", "1").contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$.id").value("1"));
    }

    @Test
    public void whenGetRequestToUsersEndPoint_thenCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users").contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$['pageable']['paged']").value("true"));

    }

    @Test
    public void whenGetRequestToUserEndPointWithNameRequestParameter_thenCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/users").param("name", "John").contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$['content'][0].['name']").value("John"));
    }

    @Test
    public void whenGetRequestToSorteredUsersEndPoint_thenCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/sortedusers").contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$['sort']['sorted']").value("true"));
    }

    @Test
    public void whenGetRequestToFilteredUsersEndPoint_thenCorrectResponse() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/filteredusers").param("name", "John").contentType(MediaType.APPLICATION_JSON_UTF8)).andExpect(MockMvcResultMatchers.status().isOk()).andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("John"));
    }
}
