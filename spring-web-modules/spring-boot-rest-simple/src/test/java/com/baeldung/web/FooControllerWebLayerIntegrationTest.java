package com.baeldung.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.persistence.service.IFooService;
import com.baeldung.web.controller.FooController;

/**
 *
 *  We'll start only the web layer.
 *
 */
@RunWith(SpringRunner.class)
@WebMvcTest(FooController.class)
public class FooControllerWebLayerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IFooService service;

    @Test
    public void whenTestMvcController_thenRetrieveExpectedResult() throws Exception {
        this.mockMvc.perform(get("/foos"))
            .andExpect(status().isOk())
            .andExpect(content().json("[]"));
    }

}
