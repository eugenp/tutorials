package com.baeldung.thymeleaf.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(printOnlyOnFailure = false)
public class FunctionCallIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetDates() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/function-call"))
        .andExpect(status().isOk())
        .andExpect(view().name("functionCall.html"));
    }

}
