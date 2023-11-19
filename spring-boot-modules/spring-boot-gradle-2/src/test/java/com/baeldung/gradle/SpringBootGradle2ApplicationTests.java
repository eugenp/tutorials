package com.baeldung.gradle;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

//@SpringBootTest
//class  {
//
//	@Test
//	void contextLoads() {
//	}
//
//}

@SpringBootTest
@AutoConfigureMockMvc
public class SpringBootGradle2ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenHelloEndPoint_whenCallingHello_ThenGettingHelloWorld() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/hello"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Hello World!"));
    }
}