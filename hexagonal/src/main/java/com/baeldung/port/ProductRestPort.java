package com.baeldung.port;


import com.baeldung.model.Product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProductRestPort {
    @PostMapping("new")
    public void create(@RequestBody Product request);

    @GetMapping("list/{id}")
    public Product view(@PathVariable Integer productId);
}