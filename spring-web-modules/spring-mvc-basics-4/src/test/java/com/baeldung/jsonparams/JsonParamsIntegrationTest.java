package com.baeldung.jsonparams;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.baeldung.jsonparams.config.JsonParamsConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { JsonParamsConfig.class }, loader = AnnotationConfigWebContextLoader.class)
public class JsonParamsIntegrationTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
            .build();
    }

    @Test
    public void whenJsonIsPassedWithPost_thenResponseOK() throws Exception {

        this.mockMvc.perform(post("/products/create").accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content("{\"id\": 1,\"name\": \"Asus Zenbook\",\"price\": 800}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("1"))
            .andExpect(jsonPath("$.name").value("Asus Zenbook"));

    }

    @Test
    public void whenJsonIsPassedWithGet_thenResponseOK() throws Exception {

        this.mockMvc.perform(get("/products/get").contentType(MediaType.APPLICATION_JSON)
            .queryParam("product", "{\"id\": 2,\"name\": \"HP EliteBook\",\"price\": 700}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("2"))
            .andExpect(jsonPath("$.name").value("HP EliteBook"));

    }

    @Test
    public void whenJsonIsPassedWithGet2_thenResponseOK() throws Exception {

        this.mockMvc.perform(get("/products/get2").contentType(MediaType.APPLICATION_JSON)
            .queryParam("product", "{\"id\": 3,\"name\": \"Dell G5 15\",\"price\": 1200}"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value("3"))
            .andExpect(jsonPath("$.name").value("Dell G5 15"));

    }

}
