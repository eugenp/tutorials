package com.baeldung.exception;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.Application;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class HttpMediaTypeNotAcceptableExceptionControllerIntegrationTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void whenHttpMediaTypeNotAcceptableExceptionTriggered_thenExceptionHandled() throws Exception {
        mockMvc.perform(post("/test").contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_PDF))
            .andExpect(content().string("acceptable MIME type:application/json"));
    }
}
