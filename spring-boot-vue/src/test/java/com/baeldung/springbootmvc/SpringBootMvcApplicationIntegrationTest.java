package com.baeldung.springbootmvc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpringBootMvcApplicationIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * If this test passes, we got a page with the thymeleaf provided variable
     * value for eventName
     */
    @Test
    public void shouldLoadCorrectIndexPage() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk()).
                andExpect(MockMvcResultMatchers.content()
                        .string(containsString("FIFA 2018")));
    }

}
