package com.baeldung.bulkandbatchapi;

import com.baeldung.bulkandbatchapi.address.Address;
import com.baeldung.bulkandbatchapi.address.AddressService;
import com.baeldung.bulkandbatchapi.customer.Customer;
import com.baeldung.bulkandbatchapi.customer.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public BatchController(CustomerService customerService,
                           AddressService addressService,
                           RequestObjectConverter<Address> addressRequestDataConverter,
                           RequestObjectConverter<Customer> customerRequestDataConverter) {
        this.customerService = customerService;
        this.addressService = addressService;
        this.addressRequestDataConverter = addressRequestDataConverter;
        this.customerRequestDataConverter = customerRequestDataConverter;
    }

    @PostMapping(path = "/customers")
    public ResponseEntity<List<CustomerBatchResponse>> batchProcessCustomers(@RequestBody @Valid @Size(min = 1, max = 20) List<CustomerBatchRequest> customerBatchRequests) {
        List<CustomerBatchResponse> customerBatchResponseList = new ArrayList<>();

        customerBatchRequests.forEach(customerBatchRequest -> {
            List<Customer> customers = customerService.processCustomers(customerBatchRequest.getCustomers(),
                    customerBatchRequest.getBatchType());
            customerBatchResponseList.add(getCustomerBatchResponse(customerBatchRequest, customers));
        });

        return ResponseEntity.ok(customerBatchResponseList);
    }

    @PostMapping(path = "/customer-address")
    public ResponseEntity<String> batchCreateCustomerWithAddress(@RequestBody @Valid @Size(min = 1, max = 20) List<BatchRequest> batchRequests) {
        batchRequests.forEach(batchRequest ->
                CompletableFuture.runAsync(() -> {
                    try {
                        processBatchRequest(batchRequest);
                    } catch (JsonProcessingException ex) {
                        throw new BatchCreateException(ex.getMessage());
                    }
                })
        );

        return new ResponseEntity<>("Batch create Customer and Address is processing async", HttpStatus.ACCEPTED);
    }

    private CustomerBatchResponse getCustomerBatchResponse(CustomerBatchRequest customerBatchRequest, List<Customer> customers) {
        CustomerBatchResponse customerBatchResponse = new CustomerBatchResponse();
        customerBatchResponse.setBatchType(customerBatchRequest.getBatchType());
        customerBatchResponse.setCustomers(customers);

        if (!CollectionUtils.isEmpty(customers) && customerBatchRequest.getCustomers().size() == customers.size()) {
            customerBatchResponse.setStatus(BatchStatus.PROCESSED);
        } else if (!CollectionUtils.isEmpty(customers) && customerBatchRequest.getCustomers().size() > customers.size()) {
            customerBatchResponse.setStatus(BatchStatus.PARTIALLY_PROCESSED);
        } else {
            customerBatchResponse.setStatus(BatchStatus.NOT_PROCESSED);
        }

        return customerBatchResponse;
    }

    private void processBatchRequest(BatchRequest batchRequest) throws JsonProcessingException {
        switch (batchRequest.getResourceType()) {
            case ADDRESS:
                addressService.createAddress(addressRequestDataConverter.convertJsonObject(batchRequest.getData(), Address.class));
                break;
            case CUSTOMER:
                customerService.createCustomer(customerRequestDataConverter.convertJsonObject(batchRequest.getData(), Customer.class));
                break;
            default:
                break;
        }
    }
}
