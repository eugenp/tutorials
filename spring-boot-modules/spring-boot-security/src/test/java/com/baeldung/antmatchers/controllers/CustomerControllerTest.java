package com.baeldung.antmatchers.controllers;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerController.class)
class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getCustomerByIdUnauthorized() throws Exception {
        mockMvc.perform(get("/customers/1")).andExpect(status().isUnauthorized());
    }

    @Test
    void getCustomerByIdForbidden() throws Exception {
        mockMvc.perform(get("/customers/1").with(user("user").roles("USER")))
          .andExpect(status().isForbidden());
    }

    @Test
    void getCustomerByIdOk() throws Exception {
        mockMvc.perform(get("/customers/1").with(user("admin").roles("ADMIN")))
          .andExpect(status().isOk());
    }

}