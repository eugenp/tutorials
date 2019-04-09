package com.baeldung.batchinserts;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.baeldung.batchinserts.CustomerController;
import com.baeldung.batchinserts.repository.CustomerRepository;
import com.baeldung.config.PersistenceConfiguration;
import com.baeldung.config.PersistenceProductConfiguration;
import com.baeldung.config.PersistenceUserConfiguration;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration(classes = { PersistenceConfiguration.class, PersistenceProductConfiguration.class, PersistenceUserConfiguration.class })
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