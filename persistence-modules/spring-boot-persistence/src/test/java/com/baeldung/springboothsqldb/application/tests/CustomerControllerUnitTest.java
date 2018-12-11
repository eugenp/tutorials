package com.baeldung.springboothsqldb.application.tests;

import java.nio.charset.Charset;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void whenPostHttpRequesttoAddCustomer_thenJSONEntityRepresentation() throws Exception {
        this.mockMvc
          .perform(MockMvcRequestBuilders.post("/addcustomer"))
          .andExpect(MockMvcResultMatchers.status().isOk())
          .andExpect(MockMvcResultMatchers.content().json("{\"id\":1,\"name\":\"Julie\",\"email\":\"Julie@domain.com\"}"));
    }
    
    @Test
    public void whenGetHttpRequesttogetCustomers_thenJSONContentType() throws Exception {
        MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
        MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
        this.mockMvc
            .perform(MockMvcRequestBuilders.get("/getcustomers"))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(contentType));
    }
}
