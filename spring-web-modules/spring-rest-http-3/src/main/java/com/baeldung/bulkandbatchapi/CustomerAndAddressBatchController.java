package com.baeldung.bulkandbatchapi;

import com.baeldung.bulkandbatchapi.address.Address;
import com.baeldung.bulkandbatchapi.address.AddressRepository;
import com.baeldung.bulkandbatchapi.customer.Customer;
import com.baeldung.bulkandbatchapi.customer.CustomerRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batch")
@Validated
public class CustomerAndAddressBatchController {

    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final CustomObjectConverter objectConverter;

    @Autowired
    public CustomerAndAddressBatchController(CustomerRepository customerRepository, AddressRepository addressRepository,
                                             CustomObjectConverter customObjectConverter) {
        this.customerRepository = customerRepository;
        this.addressRepository = addressRepository;
        this.objectConverter = customObjectConverter;
    }

    @PostMapping(path = "/customer-address")
    public ResponseEntity<String> batchUpdateCustomerWithAddress(@RequestBody @Valid @Size(min = 1, max = 20) List<BatchUpdateRequest> batchUpdateRequests) {
        batchUpdateRequests.forEach(batchUpdateRequest -> {
            switch (batchUpdateRequest.getResourceType()) {
                case ADDRESS:
                    try {
                        addressRepository.createAddress((Address)
                                objectConverter.convertObject(batchUpdateRequest.getData(), Address.class));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case CUSTOMER:
                    try {
                        customerRepository.createCustomer((Customer)
                                objectConverter.convertObject(batchUpdateRequest.getData(), Customer.class));
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    break;
            }
        });

        return ResponseEntity.ok("Customer Batch is Processed");
    }
}
