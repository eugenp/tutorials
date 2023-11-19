package com.baeldung.authresolver;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Base64;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = AuthResolverApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AuthResolverIntegrationTest {

    @Autowired
    private FilterChainProxy springSecurityFilterChain;

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders
          .webAppContextSetup(wac)
          .apply(springSecurity(springSecurityFilterChain))
          .build();
    }

    @Test
    public void givenCustomerCredential_whenWelcomeCustomer_thenExpectOk() throws Exception {
        this.mockMvc
          .perform(get("/customer/welcome")
            .header(
              "Authorization", String.format("Basic %s", Base64.getEncoder().encodeToString("customer1:pass1".getBytes()))
            )
          )
          .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void givenEmployeeCredential_whenWelcomeCustomer_thenExpect401Status() throws Exception {
        this.mockMvc
          .perform(get("/customer/welcome")
            .header(
              "Authorization", "Basic " + Base64.getEncoder().encodeToString("employee1:pass1".getBytes()))
          )
          .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenEmployeeCredential_whenWelcomeEmployee_thenExpectOk() throws Exception {
        this.mockMvc
          .perform(get("/employee/welcome")
            .header(
              "Authorization", "Basic " + Base64.getEncoder().encodeToString("employee1:pass1".getBytes()))
          )
          .andExpect(status().is2xxSuccessful());
    }

    @Test
    public void givenCustomerCredential_whenWelcomeEmployee_thenExpect401Status() throws Exception {
        this.mockMvc
          .perform(get("/employee/welcome")
            .header(
              "Authorization", "Basic " + Base64.getEncoder().encodeToString("customer1:pass1".getBytes()))
          )
          .andExpect(status().isUnauthorized());
    }
}
