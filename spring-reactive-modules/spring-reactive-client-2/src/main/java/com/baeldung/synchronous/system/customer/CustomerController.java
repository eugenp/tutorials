package com.baeldung.synchronous.system.customer;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(CustomerController.PATH_CUSTOMER)
public class CustomerController {

    public static final String PATH_CUSTOMER = "/customer";
    public static final Duration SLEEP_DURATION = Duration.ofSeconds(4);

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    Mono<Customer> getCustomer(@PathVariable("id") Long customerId) throws InterruptedException {
        TimeUnit.SECONDS.sleep(SLEEP_DURATION.getSeconds());
        return Mono.just(customerService.getBy(customerId));
    }
}
