package com.baeldung.springboothsqldb.application.tests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenPostRequesttoAddCustomer_thenJSONContentCorrecr() throws Exception {
        this.mockMvc
                .perform(post("/addcustomer"))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":1,\"name\":\"Julie\",\"email\":\"Julie@domain.com\"}"));
    }

    @Test
    public void whenGetRequesttogetCustomers_thenJSONContentCorrect() throws Exception {
        this.mockMvc
                .perform(get("/getcustomers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE + ";charset=UTF-8"));
    }
}
