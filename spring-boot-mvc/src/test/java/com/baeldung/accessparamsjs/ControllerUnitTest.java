package com.baeldung.accessparamsjs;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ControllerUnitTest {
    @Autowired
    private MockMvc mvc;

    @Test
    public void whenRequestThymeleaf_thenStatusOk() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/index")
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk());
    }

}
