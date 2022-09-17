package com.baeldung.antmatchers.controllers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.baeldung.antmatchers.AntMatchersExampleApplication;
import com.baeldung.antmatchers.config.SecurityConfiguration;

@RunWith(SpringRunner.class)
@WebMvcTest(value = CustomerController.class)
@ContextConfiguration(classes = { AntMatchersExampleApplication.class, SecurityConfiguration.class })
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getCustomerByIdUnauthorized() throws Exception {
        mockMvc.perform(get("/customers/1")).andExpect(status().isUnauthorized());
    }

    @Test
    public void getCustomerByIdForbidden() throws Exception {
        mockMvc.perform(get("/customers/1").with(user("user").roles("USER")))
          .andExpect(status().isForbidden());
    }

    @Test
    public void getCustomerByIdOk() throws Exception {
        mockMvc.perform(get("/customers/1").with(user("admin").roles("ADMIN")))
          .andExpect(status().isOk());
    }

}