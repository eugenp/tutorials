package com.baeldung.bulkandbatchapi;

import com.baeldung.bulkandbatchapi.controller.BatchController;
import com.baeldung.bulkandbatchapi.request.Address;
import com.baeldung.bulkandbatchapi.request.BatchRequest;
import com.baeldung.bulkandbatchapi.request.Customer;
import com.baeldung.bulkandbatchapi.service.AddressService;
import com.baeldung.bulkandbatchapi.service.CustomerService;
import com.baeldung.bulkandbatchapi.utility.RequestObjectConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
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
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BatchController.class)
class BatchControllerUnitTest {

    @MockBean
    private CustomerService customerService;
    @MockBean
    private AddressService addressService;
    @MockBean
    private RequestObjectConverter<Address> addressRequestDataConverter;
    @MockBean
    private RequestObjectConverter<Customer> customerRequestDataConverter;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenCustomersAndAddressAreValid_WhenCalledBatchApi_ThenShouldCreateAndReturnCustomers() throws Exception {
        Customer customer = new Customer("test1", "test1@email.com", "street1 city1");
        Address address = new Address("street1", "city1");
        BatchRequest batchRequest1 = new BatchRequest();
        batchRequest1.setMethod("POST");
        batchRequest1.setRelativeUrl("/address");
        batchRequest1.setData(address);
        BatchRequest batchRequest2 = new BatchRequest();
        batchRequest2.setMethod("PATCH");
        batchRequest2.setRelativeUrl("/customer");
        batchRequest2.setData(customer);

        List<BatchRequest> batchRequests = new ArrayList<>();
        batchRequests.add(batchRequest1);
        batchRequests.add(batchRequest2);

        Mockito.when(addressService.createAddress(address)).thenReturn(address);
        Mockito.when(customerService.updateCustomer(customer)).thenReturn(Optional.of(customer));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Accept", "application/json");

        mockMvc.perform(post("/api/batch/customer-address").headers(httpHeaders).content("[\n" +
                        "    {\n" +
                        "        \"method\": \"POST\",\n" +
                        "        \"relativeUrl\": \"/address\",\n" +
                        "        \"data\": {\n" +
                        "            \"id\": 1,\n" +
                        "            \"street\": \"test1\",\n" +
                        "            \"city\": \"test\"\n" +
                        "        }\n" +
                        "    },\n" +
                        "    {\n" +
                        "        \"method\": \"PATCH\",\n" +
                        "        \"relativeUrl\": \"/customer\",\n" +
                        "        \"data\": {\n" +
                        "            \"id\": \"1\",\n" +
                        "            \"name\": \"test1\",\n" +
                        "            \"email\": \"test1@email.com\",\n" +
                        "            \"address\": \"test1 test\"\n" +
                        "        }\n" +
                        "    }\n" +
                        "]"))
                .andExpect(status().is(HttpStatus.ACCEPTED.value()));
    }

    @Test
    void givenBatchRequestIsEmpty_WhenCalledBatchApi_ThenShouldReturnBadRequestError() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Accept", "application/json");

        mockMvc.perform(post("/api/batch/customer-address").headers(httpHeaders).content("[]"))
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()));
    }
}
