package com.baeldung.simplehexagonalex.repository.primaryQuoteProvider;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest
@AutoConfigureMockMvc
public class QuoteRequestIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenRestQuoteRequest_thenResponse() throws Exception {

        MvcResult result = this.mockMvc.perform(get("/quote/tester"))
            .andDo(print())
            .andExpect(status().isOk())
            .andReturn();

        String content = result.getResponse()
            .getContentAsString();
        
        assertThat(content).contains("tester");
        assertThat(content).contains("theysaidso");
    }
}