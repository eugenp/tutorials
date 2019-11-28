package com.hex.port;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.hex.domain.Car;

public interface CarUIPort {
    @PostMapping("create")
    public void create(@RequestBody Car request);

    @GetMapping("view/{id}")
    public Car view(@PathVariable Integer carId);
}