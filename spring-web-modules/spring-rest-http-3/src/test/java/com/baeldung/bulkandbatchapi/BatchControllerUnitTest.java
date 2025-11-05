package com.baeldung.bulkandbatchapi;

import com.baeldung.bulkandbatchapi.controller.BatchController;
import com.baeldung.bulkandbatchapi.request.Address;
import com.baeldung.bulkandbatchapi.request.Customer;
import com.baeldung.bulkandbatchapi.service.AddressService;
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

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BatchController.class)
class BatchControllerUnitTest {
    @MockBean
    private CustomerService customerService;
    @MockBean
    private AddressService addressService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void givenCustomersAndAddressAreValid_WhenCalledBatchApi_ThenShouldCreateAndReturnCustomers() throws Exception {
        Customer customer = this.getCustomer();
        customer.setAddress("street1 city1");
        Address address = this.getAddress();

        when(addressService.createAddress(address)).thenReturn(address);
        when(customerService.updateCustomer(customer)).thenReturn(Optional.of(customer));

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Accept", "application/json");

        mockMvc.perform(post("/api/batch").headers(httpHeaders).content("[" +
                        "    {" +
                        "        \"method\": \"POST\"," +
                        "        \"relativeUrl\": \"/address\"," +
                        "        \"data\": {" +
                        "            \"id\": 1," +
                        "            \"street\": \"test1\"," +
                        "            \"city\": \"test\"" +
                        "        }" +
                        "    }," +
                        "    {" +
                        "        \"method\": \"PATCH\"," +
                        "        \"relativeUrl\": \"/customer\"," +
                        "        \"data\": {" +
                        "            \"id\": \"1\"," +
                        "            \"name\": \"test1\"," +
                        "            \"email\": \"test1@email.com\"," +
                        "            \"address\": \"test1 test\"" +
                        "        }" +
                        "    }" +
                        "]")).andExpect(status().is(HttpStatus.OK.value()))
      .andExpect(content().string("Batch update is processed"));
    }

    @Test
    void givenBatchRequestIsEmpty_WhenCalledBatchApi_ThenShouldReturnBadRequestError() throws Exception {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");
        httpHeaders.add("Accept", "application/json");

        mockMvc.perform(post("/api/batch").headers(httpHeaders).content("[]"))
          .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
          .andExpect(content().string(""));
    }

    private Customer getCustomer() {
        Customer customer = new Customer();
        customer.setId(1);
        customer.setName("test1");
        customer.setEmail("test1@email.com");
        return customer;
    }

    private Address getAddress() {
        Address address = new Address();
        address.setId(0);
        address.setStreet("street1");
        address.setCity("city1");
        return address;
    }
}
