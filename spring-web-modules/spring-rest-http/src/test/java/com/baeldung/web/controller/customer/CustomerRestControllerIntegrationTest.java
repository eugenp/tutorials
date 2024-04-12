package com.baeldung.web.controller.customer;

import com.baeldung.model.Customer;
import com.baeldung.service.CustomerService;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.emptyList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CustomerRestControllerIntegrationTest {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Before
    public void setup() {
        testRestTemplate.getRestTemplate().setRequestFactory(new HttpComponentsClientHttpRequestFactory());
    }

    @Test
    public void givenExistingCustomer_whenPatched_thenOnlyPatchedFieldsUpdated() {
        Map<String, Boolean> communicationPreferences = new HashMap<>();
        communicationPreferences.put("post", true);
        communicationPreferences.put("email", true);
        Customer newCustomer = new Customer("001-555-1234", Arrays.asList("Milk", "Eggs"),
                                          communicationPreferences);
        Customer customer = customerService.createCustomer(newCustomer);


        String patchBody = "[ { \"op\": \"replace\", \"path\": \"/telephone\", \"value\": \"001-555-5678\" },\n"
                           + "{\"op\": \"add\", \"path\": \"/favorites/0\", \"value\": \"Bread\" }]";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf("application/json-patch+json"));
        ResponseEntity<Customer> patchResponse
                = testRestTemplate.exchange("/customers/{id}",
                                            HttpMethod.PATCH,
                                            new HttpEntity<>(patchBody, headers),
                                            Customer.class,
                                            customer.getId());

        Customer customerPatched = patchResponse.getBody();
        assertThat(patchResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(customerPatched.getId()).isEqualTo(customer.getId());
        assertThat(customerPatched.getTelephone()).isEqualTo("001-555-5678");
        assertThat(customerPatched.getCommunicationPreferences().get("post")).isTrue();
        assertThat(customerPatched.getCommunicationPreferences().get("email")).isTrue();
        assertThat(customerPatched.getFavorites()).containsExactly("Bread", "Milk", "Eggs");
    }
}
