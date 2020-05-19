package com.baeldung.web.controller.customer;

import com.baeldung.model.Customer;
import com.baeldung.service.CustomerService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;

import static java.util.Arrays.asList;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class CustomerRestControllerUnitTest {

    private static final String APPLICATION_JSON_PATCH_JSON = "application/json-patch+json";

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CustomerService mockCustomerService;

    @Autowired
    ApplicationContext context;

    @Test
    public void whenCustomerCreated_then201ReturnedWithNewCustomerLocation() throws Exception {
        Map<String, Boolean> communicationPreferences = new HashMap<>();
        communicationPreferences.put("post", true);
        communicationPreferences.put("email", true);
        Customer customer = new Customer("001-555-1234", asList("Milk", "Eggs"), communicationPreferences);

        Customer persistedCustomer = Customer.fromCustomer(customer);
        persistedCustomer.setId("1");

        given(mockCustomerService.createCustomer(customer)).willReturn(persistedCustomer);

        String createCustomerRequestBody = "{"
                                           + "\"telephone\": \"001-555-1234\",\n"
                                           + "\"favorites\": [\"Milk\", \"Eggs\"],\n"
                                           + "\"communicationPreferences\": {\"post\":true, \"email\":true}\n"
                                           + "}";
        mvc.perform(post("/customers")
                            .contentType(APPLICATION_JSON)
                            .content(createCustomerRequestBody))
           .andExpect(status().isCreated())
           .andExpect(redirectedUrlPattern("http://*/customers/1"));
    }

    @Test
    public void givenNonExistentCustomer_whenPatched_then404Returned() throws Exception {
        given(mockCustomerService.findCustomer("1")).willReturn(empty());

        String patchInstructions = "[{\"op\":\"replace\",\"path\": \"/telephone\",\"value\":\"001-555-5678\"}]";
        mvc.perform(patch("/customers/1")
                            .contentType(APPLICATION_JSON_PATCH_JSON)
                            .content(patchInstructions))
           .andExpect(status().isNotFound());
    }

    @Test
    public void givenExistingCustomer_whenPatched_thenReturnPatchedCustomer() throws Exception {
        Map<String, Boolean> communicationPreferences = new HashMap<>();
        communicationPreferences.put("post", true);
        communicationPreferences.put("email", true);
        Customer customer = new Customer("1", "001-555-1234", asList("Milk", "Eggs"), communicationPreferences);

        given(mockCustomerService.findCustomer("1")).willReturn(of(customer));

        String patchInstructions = "[{\"op\":\"replace\",\"path\": \"/telephone\",\"value\":\"001-555-5678\"}]";
        mvc.perform(patch("/customers/1")
                            .contentType(APPLICATION_JSON_PATCH_JSON)
                            .content(patchInstructions))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.id", is("1")))
           .andExpect(jsonPath("$.telephone", is("001-555-5678")))
           .andExpect(jsonPath("$.favorites", is(asList("Milk", "Eggs"))))
           .andExpect(jsonPath("$.communicationPreferences", is(communicationPreferences)));
    }
}
