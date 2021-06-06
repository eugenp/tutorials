package com.baeldung.hexarchitecture.productstore.ports;

import com.baeldung.hexarchitecture.productstore.domain.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProductUserInterfacePort {

    @PostMapping
    void addProduct(@RequestBody Product product);

    @GetMapping("{id}")
    Product getProduct(@PathVariable String id);
}
