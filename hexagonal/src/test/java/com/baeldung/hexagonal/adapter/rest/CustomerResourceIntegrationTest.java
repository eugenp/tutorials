package com.baeldung.hexagonal.adapter.rest;

import com.baeldung.hexagonal.adapter.persistence.model.CustomerEntity;
import com.baeldung.hexagonal.adapter.persistence.repositories.JpaCustomerRepository;
import com.baeldung.hexagonal.domain.model.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class CustomerResourceIntegrationTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private JpaCustomerRepository jpaCustomerRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void givenUsername_whenGetCustomer_thenStatus200() throws Exception {
        final String username = "user1";
        final String password = "pass1";
        final Long deposit = 100L;
        CustomerEntity customerEntity = CustomerEntity.builder()
                .username(username)
                .password(password)
                .deposit(deposit)
                .build();
        jpaCustomerRepository.save(customerEntity);

        mvc.perform(get("/api/customer/" + username)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username", is(username)))
                .andExpect(jsonPath("$.password", is(password)))
                .andExpect(jsonPath("$.deposit", is(deposit.intValue())));
    }

    @Test
    public void givenCustomer_whenRegisterCustomer_thenStatus200() throws Exception {
        final String username = "user1";
        final String password = "pass1";
        final Long deposit = 100L;
        Customer customer = Customer.builder()
                .username(username)
                .password(password)
                .deposit(deposit)
                .build();

        mvc.perform(post("/api/customer/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(customer)))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username", is(username)))
                .andExpect(jsonPath("$.password", is(password)))
                .andExpect(jsonPath("$.deposit", is(deposit.intValue())));

        Optional<CustomerEntity> customerEntity = jpaCustomerRepository.findByUsername(username);
        assertTrue(customerEntity.isPresent());
        assertEquals(customer.getUsername(), customerEntity.get().getUsername());
        assertEquals(customer.getPassword(), customerEntity.get().getPassword());
        assertEquals(customer.getDeposit(), customerEntity.get().getDeposit());
    }
}