package com.baeldung;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.model.Message;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
class SpringSecurityAnnotationParameterApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "john", password = "password", roles = "USER")
    public void whenUserHasRoleUserThenCanReadMessage() throws Exception {
        mockMvc.perform(get("/api/messages/1"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "admin", password = "password", roles = "ADMIN")
    public void whenUserHasRoleAdminThenCanReadMessage() throws Exception {
        mockMvc.perform(get("/api/messages/1"))
            .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "john", password = "password", roles = "USER")
    public void whenUserHasRoleUserThenCannotDeleteMessage() throws Exception {
        mockMvc.perform(delete("/api/messages/1"))
            .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "admin", password = "password", roles = "ADMIN")
    public void whenUserHasRoleAdminThenCanPostMessage() throws Exception {

        Message message = new Message(10, "Message 10");
        String content = objectMapper.writeValueAsString(message);

        mockMvc.perform(post("/api/messages").with(SecurityMockMvcRequestPostProcessors.csrf())
            .contentType(MediaType.APPLICATION_JSON)
            .content(content))
            .andExpect(status().isOk())
            .andExpect(content().string("Message Written"));
    }

    @Test
    @WithMockUser(username = "admin", password = "password", roles = "ADMIN")
    public void whenUserHasRoleAdminThenCanDeleteMessage() throws Exception {
        mockMvc.perform(delete("/api/messages/1").with(SecurityMockMvcRequestPostProcessors.csrf()))
            .andExpect(status().isOk())
            .andExpect(content().string("Message Deleted"));
    }
}
