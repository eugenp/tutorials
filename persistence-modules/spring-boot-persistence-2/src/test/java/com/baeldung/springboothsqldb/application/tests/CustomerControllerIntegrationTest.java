package com.baeldung.springboothsqldb.application.tests;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.baeldung.springboothsqldb.application.entities.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootTest
@AutoConfigureMockMvc
class CustomerControllerIntegrationTest {

    private static MediaType MEDIA_TYPE_JSON;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUpJsonMediaType() {
        MEDIA_TYPE_JSON = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype());
    }

    @Test
    void whenPostHttpRequesttoCustomers_thenStatusOK() throws Exception {
        Customer customer = new Customer("John", "john@domain.com");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(customer);
        
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/customers")
                        .contentType(MEDIA_TYPE_JSON)
                        .content(requestJson)
                )
                
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
    
    @Test
    void whenGetHttpRequesttoCustomers_thenStatusOK() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/customers"))
                
                .andExpect(MockMvcResultMatchers.content().contentType(MEDIA_TYPE_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
