package com.baeldung.heap;

import static org.hamcrest.Matchers.notANumber;
import static org.hamcrest.Matchers.not;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@WebMvcTest(MemoryStatusController.class)
public class MemoryStatusControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void whenGetMemoryStatistics_thenReturnJsonArray() throws Exception {
        mvc.perform(get("/memory-status").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("heapSize", not(notANumber())))
            .andExpect(jsonPath("heapMaxSize", not(notANumber())))
            .andExpect(jsonPath("heapFreeSize", not(notANumber())));
    }
}
