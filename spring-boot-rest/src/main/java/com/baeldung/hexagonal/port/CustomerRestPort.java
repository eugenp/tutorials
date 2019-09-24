package com.baeldung.hexagonal.port;

import com.baeldung.hexagonal.domain.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface CustomerRestPort {

    @PostMapping("/create")
    void create(@RequestBody Customer customer);

    @GetMapping("/find/{name}")
    Customer find(@PathVariable String name);
}
