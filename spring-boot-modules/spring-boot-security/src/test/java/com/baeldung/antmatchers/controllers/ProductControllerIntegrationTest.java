package com.baeldung.antmatchers.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.antmatchers.AntMatchersExampleApplication;
import com.baeldung.antmatchers.config.SecurityConfiguration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = ProductController.class)
@ContextConfiguration(classes = { AntMatchersExampleApplication.class, SecurityConfiguration.class })
public class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getProducts() throws Exception {
        mockMvc.perform(get("/products"))
          .andExpect(status().isOk());
    }
}