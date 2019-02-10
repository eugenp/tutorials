package com.baeldung.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

/**
 * 
 *  We'll start the whole context, but not the server. We'll mock the REST calls instead.
 *
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FooControllerAppIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenFindPaginatedRequest_thenEmptyResponse() throws Exception {
        this.mockMvc.perform(get("/auth/foos").param("page", "0")
            .param("size", "2"))
            .andExpect(status().isOk())
            .andExpect(content().json("[]"));
    }

}
