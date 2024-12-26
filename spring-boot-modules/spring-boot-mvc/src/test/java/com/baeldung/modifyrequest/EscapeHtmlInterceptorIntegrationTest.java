package com.baeldung.modifyrequest;

import java.net.URI;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.baeldung.modifyrequest.controller.UserController;
import com.fasterxml.jackson.databind.ObjectMapper;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(UserController.class)
@ActiveProfiles("interceptorExample")
public class EscapeHtmlInterceptorIntegrationTest {
    Logger logger = LoggerFactory.getLogger(EscapeHtmlInterceptorIntegrationTest.class);

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenInterceptor_whenEscapeHtmlInterceptor_thenEscapeHtml() throws Exception {
        Map<String, String> requestBody = Map.of(
            "name", "James Cameron",
            "email", "<script>alert()</script>james@gmail.com"
        );

        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post(URI.create("/save"))
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(requestBody))).andExpect(MockMvcResultMatchers.status()
            .is4xxClientError());
    }
}
