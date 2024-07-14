package com.baeldung.jvmargs;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest(classes = MyApplication.class)
@AutoConfigureMockMvc
class MyControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenGetLength_thenZeroIsReturned() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/length"))
          .andExpect(MockMvcResultMatchers.status()
            .isOk())
          .andExpect(MockMvcResultMatchers.content()
            .string("0"));
    }

}
