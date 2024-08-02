package com.baeldung.httpstatuscode;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StatusController.class)
public class StatusControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenGetRequestSentToResource_thenReturnStatusOk() throws Exception {
        mockMvc.perform(get("/resource")).andExpect(status().is2xxSuccessful());
    }
    @Test
    public void whenGetRequestSentToException_thenRetunStatusNotFound() throws Exception {
        mockMvc.perform(get("/exception")).andExpect(status().isNotFound());
    }

    @Test
    public void whenGetRequestSentToCustomException_thenReturnStatusGone() throws Exception {
        mockMvc.perform(get("/custom-exception")).andExpect(status().is4xxClientError());
    }
}
