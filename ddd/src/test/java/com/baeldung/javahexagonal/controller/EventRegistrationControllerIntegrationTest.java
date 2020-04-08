package com.baeldung.javahexagonal.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.javahexagonal.service.EventService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EventRegistrationController.class)
class EventRegistrationControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private EventService service;

    @Test
    void name() throws Exception {
        mvc.perform(post("/registration/junit").contentType(MediaType.APPLICATION_JSON)
            .content("{\"name\":\"John Doe\",\"mail\":\"you@example.org\"}"))
            .andExpect(status().isOk());

        verify(service).registerAttendee(eq("junit"), any());
    }
}
