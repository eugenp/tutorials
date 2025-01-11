package com.baeldung.synchronous.gateway;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(ApiGatewayController.PATH_CUSTOMER_INFO)
public class ApiGatewayController {

    protected static final String PATH_CUSTOMER_INFO = "/customer-info";

    private final CustomerInfoService customerInfoService;

    public ApiGatewayController(CustomerInfoService customerInfoService) {
        this.customerInfoService = customerInfoService;
    }

    @GetMapping("/{id}")
    CustomerInfo getCustomerInfo(@PathVariable("id") Long customerId) {
        return customerInfoService.getCustomerInfo(customerId);
    }
}
