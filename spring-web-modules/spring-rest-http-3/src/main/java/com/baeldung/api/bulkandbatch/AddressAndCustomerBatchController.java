package com.baeldung.api.bulkandbatch;

import com.baeldung.api.bulkandbatch.address.Address;
import com.baeldung.api.bulkandbatch.address.AddressController;
import com.baeldung.api.bulkandbatch.customer.Customer;
import com.baeldung.api.bulkandbatch.customer.CustomerController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/batch")
public class AddressAndCustomerBatchController {

    private final CustomerController customerController;

    private final AddressController addressController;

    public AddressAndCustomerBatchController(@Autowired CustomerController customerController, @Autowired AddressController addressController) {
        this.customerController = customerController;
        this.addressController = addressController;
    }

    @PostMapping(path = "/address-customer")
    public ResponseEntity<String> batchUpdateAddressAndCustomer(@RequestBody List<BatchRequest> batchRequests) {

        batchRequests.forEach(batchRequest -> {
            switch (batchRequest.getResourceType()) {
                case ADDRESS:
                        addressController.createOrUpdateAddress(getAddress(batchRequest.getData()));
                    break;
                case CUSTOMER:
                        customerController.updateCustomer(getCustomer(batchRequest.getData()));
                    break;
                default:
                    break;
            }
        });

        return ResponseEntity.ok("Customer Batch Processed Successfully");
    }

    private Address getAddress(String address) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(address, Address.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Customer getCustomer(String customer) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(customer, Customer.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
