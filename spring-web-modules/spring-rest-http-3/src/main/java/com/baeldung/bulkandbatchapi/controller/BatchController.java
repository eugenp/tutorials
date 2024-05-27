package com.baeldung.bulkandbatchapi.controller;

import com.baeldung.bulkandbatchapi.exception.BatchException;
import com.baeldung.bulkandbatchapi.utility.RequestObjectConverter;
import com.baeldung.bulkandbatchapi.request.Address;
import com.baeldung.bulkandbatchapi.service.AddressService;
import com.baeldung.bulkandbatchapi.request.Customer;
import com.baeldung.bulkandbatchapi.service.CustomerService;
import com.baeldung.bulkandbatchapi.request.BatchRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/batch")
@Validated
public class BatchController {

    private final CustomerService customerService;
    private final AddressService addressService;
    private final RequestObjectConverter<Address> addressRequestDataConverter;
    private final RequestObjectConverter<Customer> customerRequestDataConverter;

    @Autowired
    public BatchController(CustomerService customerService, AddressService addressService,
                           RequestObjectConverter<Address> addressRequestDataConverter,
                           RequestObjectConverter<Customer> customerRequestDataConverter) {
        this.customerService = customerService;
        this.addressService = addressService;
        this.addressRequestDataConverter = addressRequestDataConverter;
        this.customerRequestDataConverter = customerRequestDataConverter;
    }

    @PostMapping(path = "/customer-address")
    public ResponseEntity<String> batchUpdateCustomerWithAddress(@RequestBody @Valid @Size(min = 1, max = 20) List<BatchRequest> batchRequests) {
        batchRequests.forEach(batchRequest -> CompletableFuture.runAsync(() -> {
            try {
                processBatchRequest(batchRequest);
            } catch (IOException ex) {
                throw new BatchException(ex.getMessage());
            }
        }));

        return new ResponseEntity<>("Batch update is processing async", HttpStatus.ACCEPTED);
    }

    private void processBatchRequest(BatchRequest batchRequest) throws IOException {
        if (batchRequest.getMethod().equals("POST") && batchRequest.getRelativeUrl().equals("/address")) {
            addressService.createAddress(addressRequestDataConverter.convertJsonObject(batchRequest.getData(), Address.class));
        } else if (batchRequest.getMethod().equals("PATCH") && batchRequest.getRelativeUrl().equals("/customer")) {
            customerService.updateCustomer(customerRequestDataConverter.convertJsonObject(batchRequest.getData(), Customer.class));
        }
    }
}
