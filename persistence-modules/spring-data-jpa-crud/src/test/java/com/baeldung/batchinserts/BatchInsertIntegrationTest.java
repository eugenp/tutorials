package com.baeldung.batchinserts;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.baeldung.boot.Application;
import com.baeldung.boot.daos.CustomerRepository;
import com.baeldung.boot.web.controllers.CustomerController;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=Application.class)
@AutoConfigureMockMvc
@ActiveProfiles("stats")
public class BatchInsertIntegrationTest {

    @Autowired
    private CustomerRepository customerRepository;
    private MockMvc mockMvc;
    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup( new CustomerController(customerRepository))
            .build();
    }

    @Test
    public void whenInsertingCustomers_thenCustomersAreCreated() throws Exception {
        this.mockMvc.perform(post("/customers"))
            .andExpect(status().isOk());
    }

}