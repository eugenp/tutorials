package com.baeldung.web;

import com.baeldung.persistence.service.IFooService;
import com.baeldung.web.controller.FooController;
import com.baeldung.web.exception.CustomException3;
import com.baeldung.web.exception.CustomException4;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 *
 *  We'll start only the web layer.
 *
 */
@WebMvcTest(FooController.class)
public class GlobalExceptionHandlerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private IFooService service;

    @MockitoBean
    private ApplicationEventPublisher publisher;

    @Test
    public void delete_forException3_fromService() throws Exception {
        when(service.findAll())
                .thenThrow(new CustomException3());
        this.mockMvc
                .perform(get("/foos"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void delete_forException4Json_fromService() throws Exception {
        when(service.findAll())
                .thenThrow(new CustomException4("TEST"));
        this.mockMvc
                .perform(get("/foos").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    public void delete_forException4Text_fromService() throws Exception {
        when(service.findAll())
                .thenThrow(new CustomException4("TEST"));
        this.mockMvc
                .perform(
                        get("/foos")
                                .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

}
