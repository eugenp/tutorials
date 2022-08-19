package com.baeldung.swaggerresponses.controller;

import com.baeldung.swaggerresponses.response.CustomerResponse;
import com.baeldung.swaggerresponses.response.ErrorResponse;
import com.baeldung.swaggerresponses.service.CustomerService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("Customer Controller")
@RestController
@RequestMapping("/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @ApiOperation(value = "Gets customer by ID", response = CustomerResponse.class, notes = "Customer must exist")
    @ApiResponses(value = {
        @ApiResponse(code = 400, message = "Invalid ID supplied"),
        @ApiResponse(code = 404, message = "Customer not found"),
        @ApiResponse(code = 500, message = "Internal server error", response = ErrorResponse.class) })
    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponse> getCustomer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(customerService.getById(id));
    }
}
