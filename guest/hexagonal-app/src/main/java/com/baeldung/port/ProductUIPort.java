package com.baeldung.port;


import com.baeldung.domain.Product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface ProductUIPort {
    @PostMapping("create")
    public void create(@RequestBody Product request);

    @GetMapping("view/{id}")
    public Product view(@PathVariable Integer userId);
}