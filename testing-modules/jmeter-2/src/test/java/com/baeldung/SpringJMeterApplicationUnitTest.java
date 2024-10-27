package com.baeldung;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpringJMeterApplicationUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenClientRequestsGetUuid_thenReceivesValidUuid() throws Exception {
        mockMvc.perform(get("/api/uuid"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Test message... ")));
    }
}
