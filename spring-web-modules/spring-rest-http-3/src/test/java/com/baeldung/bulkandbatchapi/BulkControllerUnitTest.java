package com.baeldung.bulkandbatchapi;

import com.baeldung.bulkandbatchapi.controller.BulkController;
import com.baeldung.bulkandbatchapi.request.Customer;
import com.baeldung.bulkandbatchapi.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BulkController.class)
class BulkControllerUnitTest {

    @MockBean
    private CustomerService customerService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenCustomersIsValid_WhenCalledBulkCreateCustomers_ThenShouldCreateAndReturnCustomers() throws Exception {
        Customer customer1 = this.getCustomer("test1", "test1@email.com", "address1");
        Customer customer2 = this.getCustomer("test2", "test2@email.com", "address2");
        List<Customer> customers = Arrays.asList(customer1, customer2);

        when(customerService.createCustomers(customers)).thenReturn(customers);
        when(customerService.createCustomers(customers)).thenReturn(customers);

        mockMvc.perform(post("/api/customers").headers(getHttpHeaders("bulk")).content("[" +
                        "    {" +
                        "        \"name\": \"test1\"," +
                        "        \"email\": \"test1@email.com\"," +
                        "        \"address\": \"address1\"" +
                        "    }," +
                        "    {" +
                        "        \"name\": \"test2\"," +
                        "        \"email\": \"test2@email.com\"," +
                        "        \"address\": \"address2\"" +
                        "    }" +
                        "]"))
          .andExpect(status().is(HttpStatus.CREATED.value()))
          .andExpect(content().string("[]"));
    }

    @Test
    void givenCustomersNotExists_WhenCalledBulkCreateCustomers_WithInvalidData_ThenShouldReturnBadRequestError() throws Exception {
        mockMvc.perform(post("/api/customers").headers(getHttpHeaders("bulk")).content("[]"))
          .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
          .andExpect(content().string(""));
    }

    @Test
    void givenActionTypeHeaderIsMissing_WhenCalledBulkCreateCustomers_ThenShouldReturnBadRequestError() throws Exception {
        mockMvc.perform(post("/api/customers"))
          .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
          .andExpect(content().string(""));
    }

    @Test
    void givenCustomersAreValid_WhenCalledBulkProcessCustomers_ThenShouldProcessCustomers_ReturnValidData() throws Exception {
        Customer customer1 = this.getCustomer("test4", "test4@email.com", "address1");
        Customer customer2 = this.getCustomer("test1-update", "test2@email.com", "address1-update");
        customer2.setId(1);

        when(customerService.createCustomer(customer1)).thenReturn(Optional.of(customer1));
        when(customerService.updateCustomer(customer1)).thenReturn(Optional.of(customer2));

        mockMvc.perform(post("/api/customers/bulk").headers(getHttpHeaders("singular")).content("[" +
                        "    {" +
                        "        \"bulkActionType\": \"CREATE\"," +
                        "        \"customers\": [" +
                        "            {" +
                        "                \"name\": \"test4\"," +
                        "                \"email\": \"test4@email.com\"," +
                        "                \"address\": \"address1\"" +
                        "            }" +
                        "        ]" +
                        "    }," +
                        "    {" +
                        "        \"bulkActionType\": \"UPDATE\"," +
                        "        \"customers\": [" +
                        "            {" +
                        "                \"id\": 1," +
                        "                \"name\": \"test1-update\"," +
                        "                \"email\": \"test1@email.com\"," +
                        "                \"address\": \"address1-update\"" +
                        "            }" +
                        "        ]" +
                        "}" +
                        "]"))
          .andExpect(status().is(HttpStatus.MULTI_STATUS.value()));
    }

    @Test
    void givenCustomerAreValid_WhenCalledSingleCustomer_ThenShouldProcessCustomers_ReturnValidData() throws Exception {
        Customer customer1 = this.getCustomer("test4", "test4@email.com", "address1");
        when(customerService.createCustomer(customer1)).thenReturn(Optional.of(customer1));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Accept", "application/json");

        mockMvc.perform(post("/api/customers/bulk").headers(httpHeaders).content("[" +
                        "    {" +
                        "        \"bulkActionType\": \"CREATE\"," +
                        "        \"customers\": [" +
                        "            {" +
                        "                \"name\": \"test4\"," +
                        "                \"email\": \"test4@email.com\"," +
                        "                \"address\": \"address4\"" +
                        "            }" +
                        "        ]" +
                        "    }" +
                        "]"))
          .andExpect(status().is(HttpStatus.MULTI_STATUS.value()));
    }

    private Customer getCustomer(String name, String email, String address) {
        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setAddress(address);
        return customer;
    }

    private HttpHeaders getHttpHeaders(String actionType) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Accept", "application/json");
        httpHeaders.add("X-ActionType", actionType);
        return httpHeaders;
    }
}
