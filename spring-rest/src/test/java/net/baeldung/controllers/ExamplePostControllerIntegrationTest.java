package net.baeldung.controllers;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExamplePostControllerIntegrationTest {

    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
    private final String jsonBody = "{\"username\": \"username\", \"password\": \"password\"}";

    @Before
    public void preTest() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void responseBodyTest() {
        try {
            mockMvc.perform(post("/post/response")
                    .content(jsonBody)
                    .contentType("application/json"))
                    .andDo(print())
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                    .andExpect(jsonPath("$.text").value("Thanks For Posting!!!"));
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    @After
    public void postTest() {
        mockMvc = null;
    }
}