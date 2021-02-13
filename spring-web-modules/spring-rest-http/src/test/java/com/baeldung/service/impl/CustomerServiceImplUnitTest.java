package com.baeldung.service.impl;


import com.baeldung.model.Customer;
import com.baeldung.service.CustomerIdGenerator;
import com.baeldung.service.CustomerService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;


import static com.baeldung.model.Customer.fromCustomer;
import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplUnitTest {

    @Mock
    private CustomerIdGenerator mockCustomerIdGenerator;

    private CustomerService customerService;

    @Before
    public void setup() {
        customerService = new CustomerServiceImpl(mockCustomerIdGenerator);
    }

    @Test
    public void whenCustomerIsCreated_thenNewCustomerDetailsAreCorrect() {
        Map<String, Boolean> communicationPreferences = new HashMap<>();
        communicationPreferences.put("post", true);
        communicationPreferences.put("email", true);
        Customer customer = new Customer("001-555-1234", asList("Milk", "Eggs"), communicationPreferences);
        given(mockCustomerIdGenerator.generateNextId()).willReturn(1);

        Customer newCustomer = customerService.createCustomer(customer);

        assertThat(newCustomer.getId()).isEqualTo("1");
        assertThat(newCustomer.getTelephone()).isEqualTo("001-555-1234");
        assertThat(newCustomer.getFavorites()).containsExactly("Milk", "Eggs");
        assertThat(newCustomer.getCommunicationPreferences()).isEqualTo(communicationPreferences);
    }

    @Test
    public void givenNonExistentCustomer_whenCustomerIsLookedUp_thenCustomerCanNotBeFound() {
        assertThat(customerService.findCustomer("CUST12345")).isEqualTo(empty());
    }

    @Test
    public void whenCustomerIsCreated_thenCustomerCanBeFound() {
        Map<String, Boolean> communicationPreferences = new HashMap<>();
        communicationPreferences.put("post", true);
        communicationPreferences.put("email", true);
        Customer customer = new Customer("001-555-1234", asList("Milk", "Eggs"), communicationPreferences);
        given(mockCustomerIdGenerator.generateNextId()).willReturn(7890);

        customerService.createCustomer(customer);
        Customer lookedUpCustomer = customerService.findCustomer("7890").get();

        assertThat(lookedUpCustomer.getId()).isEqualTo("7890");
        assertThat(lookedUpCustomer.getTelephone()).isEqualTo("001-555-1234");
        assertThat(lookedUpCustomer.getFavorites()).containsExactly("Milk", "Eggs");
        assertThat(lookedUpCustomer.getCommunicationPreferences()).isEqualTo(communicationPreferences);
    }

    @Test
    public void whenCustomerUpdated_thenDetailsUpdatedCorrectly() {
        given(mockCustomerIdGenerator.generateNextId()).willReturn(7890);
        Map<String, Boolean> communicationPreferences = new HashMap<>();
        communicationPreferences.put("post", true);
        communicationPreferences.put("email", true);
        Customer customer = new Customer("001-555-1234", asList("Milk", "Eggs"), communicationPreferences);
        Customer newCustomer = customerService.createCustomer(customer);

        Customer customerWithUpdates = fromCustomer(newCustomer);
        customerWithUpdates.setTelephone("001-555-6789");
        customerService.updateCustomer(customerWithUpdates);
        Customer lookedUpCustomer = customerService.findCustomer("7890").get();

        assertThat(lookedUpCustomer.getId()).isEqualTo("7890");
        assertThat(lookedUpCustomer.getTelephone()).isEqualTo("001-555-6789");
        assertThat(lookedUpCustomer.getFavorites()).containsExactly("Milk", "Eggs");
        assertThat(lookedUpCustomer.getCommunicationPreferences()).isEqualTo(communicationPreferences);
    }
}