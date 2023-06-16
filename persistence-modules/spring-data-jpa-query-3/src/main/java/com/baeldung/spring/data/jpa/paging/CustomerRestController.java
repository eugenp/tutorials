package com.baeldung.spring.data.jpa.paging;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CustomerRestController {

    private final CustomerService customerService;

    public CustomerRestController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/api/customers")
    public ResponseEntity<Page<Customer>> getCustomers(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {

        Page<Customer> customerPage = customerService.getCustomers(page, size);
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Page-Number", String.valueOf(customerPage.getNumber()));
        headers.add("X-Page-Size", String.valueOf(customerPage.getSize()));

        return ResponseEntity.ok()
          .headers(headers)
          .body(customerPage);
    }
}
