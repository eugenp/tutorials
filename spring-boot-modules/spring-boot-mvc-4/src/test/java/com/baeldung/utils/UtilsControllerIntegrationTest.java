package com.baeldung.utils;

import com.baeldung.utils.controller.UtilsController;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UtilsControllerIntegrationTest {

    @InjectMocks
    private UtilsController utilsController;

    private MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        openMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(utilsController).build();

    }

    @Test
    void givenParameter_setRequestParam_andSetSessionAttribute() throws Exception {
        String param = "testparam";
        this.mockMvc.perform(post("/setParam").param("param", param).sessionAttr("parameter", param)).andExpect(status().isOk());
    }

}
