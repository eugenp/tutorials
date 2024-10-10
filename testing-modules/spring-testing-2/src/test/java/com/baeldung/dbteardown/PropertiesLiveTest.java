package com.baeldung.dbteardown;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("hsql")
@TestPropertySource(properties = { "spring.jpa.hibernate.ddl-auto=create" })
public class PropertiesLiveTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenPostHttpRequestCustomers_thenStatusOK() throws Exception {
        Customer customer = new Customer("John", "john@domain.com");
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
        ObjectWriter objectWriter = mapper.writer()
            .withDefaultPrettyPrinter();
        String requestJson = objectWriter.writeValueAsString(customer);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/customers")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
            .andExpect(MockMvcResultMatchers.status()
                .isOk());
    }

    @Test
    void whenGetCustomerByName_thenStatusOK() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/John"))
            .andExpect(MockMvcResultMatchers.status()
                .isOk())
            .andExpect(MockMvcResultMatchers.content()
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.jsonPath("$.name")
                .value("John"));
    }

    @Test
    void whenGetCustomerByName_notFound_thenStatus404() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/customers/Doe")
                .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status()
                .isNotFound());
    }
}
