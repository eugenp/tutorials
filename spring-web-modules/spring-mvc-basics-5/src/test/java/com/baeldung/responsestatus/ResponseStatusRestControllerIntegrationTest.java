package com.baeldung.responsestatus;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ResponseStatusRestControllerIntegrationTest {

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new ResponseStatusRestController())
            .build();
    }

    @Test
    public void whenTeapotEndpointCalled_thenTeapotResponseObtained() throws Exception {
        this.mockMvc.perform(get("/teapot"))
            .andExpect(status().isIAmATeapot());
    }

    @Test
    public void whenEmptyNoContentEndpointCalled_thenNoContentResponseObtained() throws Exception {
        this.mockMvc.perform(get("/empty"))
            .andExpect(status().isNoContent());
    }

    @Test
    public void whenEmptyWithoutResponseStatusEndpointCalled_then200ResponseObtained() throws Exception {
        this.mockMvc.perform(get("/empty-no-responsestatus"))
            .andExpect(status().isOk());
    }

    @Test
    public void whenCreateWithCreatedEndpointCalled_thenCreatedResponseObtained() throws Exception {
        this.mockMvc.perform(post("/create"))
            .andExpect(status().isCreated());
    }

    @Test
    public void whenCreateWithoutResponseStatusEndpointCalled_thenCreatedResponseObtained() throws Exception {
        this.mockMvc.perform(post("/create-no-responsestatus"))
            .andExpect(status().isOk());
    }

}
