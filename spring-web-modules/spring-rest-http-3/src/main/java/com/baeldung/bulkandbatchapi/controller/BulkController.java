package com.baeldung.bulkandbatchapi.controller;

import com.baeldung.bulkandbatchapi.request.BulkActionType;
import com.baeldung.bulkandbatchapi.request.Customer;
import com.baeldung.bulkandbatchapi.request.CustomerBulkRequest;
import com.baeldung.bulkandbatchapi.response.BulkStatus;
import com.baeldung.bulkandbatchapi.response.CustomerBulkResponse;
import com.baeldung.bulkandbatchapi.service.CustomerService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api")
@Validated
public class BulkController {

    private final CustomerService customerService;
    private final EnumMap<BulkActionType, Function<Customer, Optional<Customer>>> bulkActionFuncMap = new EnumMap<>(BulkActionType.class);

    public BulkController(CustomerService customerService) {
        this.customerService = customerService;
        bulkActionFuncMap.put(BulkActionType.CREATE, customerService::createCustomer);
        bulkActionFuncMap.put(BulkActionType.UPDATE, customerService::updateCustomer);
        bulkActionFuncMap.put(BulkActionType.DELETE, customerService::deleteCustomer);
    }

    @PostMapping(path = "/customers")
    public ResponseEntity<List<Customer>> createCustomers(@RequestHeader(value = "X-ActionType") String actionType, @RequestBody @Valid @Size(min = 1, max = 20) List<Customer> customers) {
        List<Customer> customerList = actionType.equals("bulk") ? customerService.createCustomers(customers) :
                Collections.singletonList(customerService.createCustomer(customers.get(0)).orElse(null));

        return new ResponseEntity<>(customerList, HttpStatus.CREATED);
    }

    @PostMapping(path = "/customers/bulk")
    public ResponseEntity<List<CustomerBulkResponse>> bulkProcessCustomers(@RequestBody @Valid @Size(min = 1, max = 20) List<CustomerBulkRequest> customerBulkRequests) {
        List<CustomerBulkResponse> customerBulkResponseList = new ArrayList<>();

        customerBulkRequests.forEach(customerBulkRequest -> {
            List<Customer> customers = customerBulkRequest.getCustomers().stream()
                    .map(bulkActionFuncMap.get(customerBulkRequest.getBulkActionType()))
                    .filter(Optional::isPresent)
                    .map(Optional::get)
                    .collect(toList());

            BulkStatus bulkStatus = getBulkStatus(customerBulkRequest.getCustomers(), customers);
            customerBulkResponseList.add(new CustomerBulkResponse(customers, customerBulkRequest.getBulkActionType(), bulkStatus));
        });

        return new ResponseEntity<>(customerBulkResponseList, HttpStatus.MULTI_STATUS);
    }

    private BulkStatus getBulkStatus(List<Customer> customersInRequest, List<Customer> customersProcessed) {
        if (!customersProcessed.isEmpty()) {
            return customersProcessed.size() == customersInRequest.size() ?
                BulkStatus.PROCESSED :
                BulkStatus.PARTIALLY_PROCESSED;
        }

        return BulkStatus.NOT_PROCESSED;
    }
}
