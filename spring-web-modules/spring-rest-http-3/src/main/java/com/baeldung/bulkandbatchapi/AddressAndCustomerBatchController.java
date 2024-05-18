package com.baeldung.bulkandbatchapi;

import com.baeldung.bulkandbatchapi.address.Address;
import com.baeldung.bulkandbatchapi.address.AddressController;
import com.baeldung.bulkandbatchapi.customer.Customer;
import com.baeldung.bulkandbatchapi.customer.CustomerController;
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
public class AddressAndCustomerBatchController {

    private final CustomerController customerController;

    private final AddressController addressController;

    private final CustomJsonConverter objectConverter;

    @Autowired
    public AddressAndCustomerBatchController(CustomerController customerController, AddressController addressController, CustomJsonConverter customJsonConverter) {
        this.customerController = customerController;
        this.addressController = addressController;
        this.objectConverter = customJsonConverter;
    }

    @PostMapping(path = "/customer-address")
    public ResponseEntity<String> batchUpdateCustomerWithAddress(@RequestBody @Valid @Size(min = 1, max = 20) List<BatchUpdateRequest> batchUpdateRequests) {
        batchUpdateRequests.forEach(batchUpdateRequest -> {
            switch (batchUpdateRequest.getResourceType()) {
                case ADDRESS:
                    addressController.createAddress((Address) objectConverter.convertObjectToType(batchUpdateRequest.getData(), Address.class));
                    break;
                case CUSTOMER:
                    customerController.updateCustomer((Customer) objectConverter.convertObjectToType(batchUpdateRequest.getData(), Customer.class));
                    break;
                default:
                    break;
            }
        });

        return ResponseEntity.ok("Customer Batch Processed Successfully");
    }
}
