package com.baeldung.bulkandbatchapi.controller;

import com.baeldung.bulkandbatchapi.request.Address;
import com.baeldung.bulkandbatchapi.service.AddressService;
import com.baeldung.bulkandbatchapi.request.Customer;
import com.baeldung.bulkandbatchapi.service.CustomerService;
import com.baeldung.bulkandbatchapi.request.BatchRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Validated
public class BatchController {

    private final CustomerService customerService;
    private final AddressService addressService;
    private final ObjectMapper objectMapper;

    public BatchController(CustomerService customerService, AddressService addressService, ObjectMapper objectMapper) {
        this.customerService = customerService;
        this.addressService = addressService;
        this.objectMapper = objectMapper;
    }

    @PostMapping(path = "/batch")
    public ResponseEntity<String> batchUpdateCustomerWithAddress(@RequestBody @Valid @Size(min = 1, max = 20) List<BatchRequest> batchRequests) {
        batchRequests.forEach(batchRequest -> {
            if (batchRequest.getMethod().equals(HttpMethod.POST) && batchRequest.getRelativeUrl().equals("/address")) {
                addressService.createAddress(objectMapper.convertValue(batchRequest.getData(), Address.class));
            } else if (batchRequest.getMethod().equals(HttpMethod.PATCH) && batchRequest.getRelativeUrl().equals("/customer")) {
                customerService.updateCustomer(objectMapper.convertValue(batchRequest.getData(), Customer.class));
            }
        });

        return new ResponseEntity<>("Batch update is processed", HttpStatus.OK);
    }
}
