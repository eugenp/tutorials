package com.baeldung.sharedthreadvars;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class RestApiIntegrationTest {

    @Autowired
    MockMvc mvc;
    Random random = new Random();

    @Test
    void givenRandomString_whenPost_thenCanRetrieveItViaGet() throws Exception {
        String originalName = UUID.randomUUID()
            .toString();

        String uuid = mvc.perform(post("/api").contentType(MediaType.TEXT_PLAIN)
            .content(originalName))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        String retrievedName = mvc.perform(get("/api").param("uuid", uuid))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

        assertEquals(originalName, retrievedName);
    }
}
