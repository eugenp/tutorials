package com.baeldung.utils;

import com.baeldung.utils.controller.UtilsController;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UtilsControllerIntegrationTest {

    @InjectMocks
    private UtilsController utilsController;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(utilsController).build();

    }

    @Test
    public void givenParameter_setRequestParam_andSetSessionAttribute() throws Exception {
        String param = "testparam";
        this.mockMvc.perform(post("/setParam").param("param", param).sessionAttr("parameter", param)).andExpect(status().isOk());
    }

}
