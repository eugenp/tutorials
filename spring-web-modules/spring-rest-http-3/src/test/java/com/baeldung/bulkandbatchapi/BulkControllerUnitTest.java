package com.baeldung.bulkandbatchapi;

import com.baeldung.bulkandbatchapi.controller.BulkController;
import com.baeldung.bulkandbatchapi.request.BulkActionType;
import com.baeldung.bulkandbatchapi.request.Customer;
import com.baeldung.bulkandbatchapi.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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
        Customer customer1 = new Customer("test1", "test1@email.com", "address1");
        Customer customer2 = new Customer("test2", "test2@email.com", "address2");
        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);
        customers.add(customer2);

        Mockito.when(customerService.createCustomers(customers)).thenReturn(customers);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Accept", "application/json");
        httpHeaders.add("X-ActionType", "bulk");
        mockMvc.perform(post("/api/customers").headers(httpHeaders).content("[\n" +
                        "    {\n" +
                        "        \"name\": \"test1\",\n" +
                        "        \"email\": \"test1@email.com\",\n" +
                        "        \"address\": \"address1\"\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"name\": \"test2\",\n" +
                        "        \"email\": \"test2@email.com\",\n" +
                        "        \"address\": \"address2\"\n" +
                        "    }\n" +
                        "]"))
                .andExpect(status().is(HttpStatus.OK.value()));
    }

    @Test
    void givenCustomersDoesnotExists_WhenCalledBulkCreateCustomers_WithInvalidData_ThenShouldReturnBadRequestError() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Accept", "application/json");
        httpHeaders.add("X-Action", "bulk");

        mockMvc.perform(post("/api/customers").headers(httpHeaders).content("[]"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }

    @Test
    void givenCustomersAreValid_WhenCalledBulkProcessCustomers_ThenShouldProcessCustomers_ReturnValidData() throws Exception {
        Customer customer1 = new Customer("test4", "test4@email.com", "address1");
        Customer customer2 = new Customer("test1-update", "test2@email.com", "address1-update");
        Customer customer3 = new Customer("test3", "test3@email.com", "address");
        List<Customer> customersList1 = new ArrayList<>();
        List<Customer> customersList2 = new ArrayList<>();
        List<Customer> customersList3 = new ArrayList<>();
        customersList1.add(customer1);
        customersList2.add(customer2);
        customersList3.add(customer3);

        Mockito.when(customerService.processCustomers(customersList1, BulkActionType.CREATE)).thenReturn(customersList1);
        Mockito.when(customerService.processCustomers(customersList2, BulkActionType.UPDATE)).thenReturn(customersList2);
        Mockito.when(customerService.processCustomers(customersList3, BulkActionType.DELETE)).thenReturn(customersList3);

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Accept", "application/json");
        httpHeaders.add("X-ActionType", "bulk");

        mockMvc.perform(post("/api/customers/bulk").headers(httpHeaders).content("[\n" +
                        "    {\n" +
                        "        \"bulkActionType\": \"CREATE\",\n" +
                        "        \"customers\": [\n" +
                        "            {\n" +
                        "                \"name\": \"test4\",\n" +
                        "                \"email\": \"test4@email.com\",\n" +
                        "                \"address\": \"address4\"\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"bulkActionType\": \"UPDATE\",\n" +
                        "        \"customers\": [\n" +
                        "            {\n" +
                        "                \"id\": 1,\n" +
                        "                \"name\": \"test1-update\",\n" +
                        "                \"email\": \"test1@email.com\",\n" +
                        "                \"address\": \"address1-update\"\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"bulkActionType\": \"DELETE\",\n" +
                        "        \"customers\": [\n" +
                        "            {\n" +
                        "                \"id\": 3,\n" +
                        "                \"name\": \"test3\",\n" +
                        "                \"email\": \"test3@email.com\",\n" +
                        "                \"address\": \"address3\"\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    }\n" +
                        "]"))
                .andExpect(status().is(HttpStatus.OK.value()));
    }
}
