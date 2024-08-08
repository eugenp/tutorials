package com.baeldung.httpstatuscode;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(StatusController.class)
class StatusControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGetRequestSentToResource_thenReturnStatusOk() throws Exception {
        mockMvc.perform(get("/resource"))
            .andExpect(status().is2xxSuccessful());
    }

    @Test
    void whenGetRequestSentToException_thenReturnStatusNotFound() throws Exception {
        mockMvc.perform(get("/exception"))
            .andExpect(status().isNotFound());
    }

    @Test
    void whenGetRequestSentToCustomException_thenReturnStatusGone() throws Exception {
        mockMvc.perform(get("/custom-exception"))
            .andExpect(status().is4xxClientError());
    }
}
