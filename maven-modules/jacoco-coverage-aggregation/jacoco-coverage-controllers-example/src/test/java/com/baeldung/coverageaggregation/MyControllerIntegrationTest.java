package com.baeldung.coverageaggregation;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = MyApplication.class)
@AutoConfigureMockMvc
class MyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenFullyTested_ThenCorrectText() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/tested"))
          .andExpect(MockMvcResultMatchers.status()
            .isOk())
          .andExpect(MockMvcResultMatchers.content()
            .string("covered by unit and integration tests"));
    }

    @Test
    void whenIndirectlyTestingServiceMethod_ThenCorrectText() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/indirecttest"))
          .andExpect(MockMvcResultMatchers.status()
            .isOk())
          .andExpect(MockMvcResultMatchers.content()
            .string("covered by integration test"));
    }

}
