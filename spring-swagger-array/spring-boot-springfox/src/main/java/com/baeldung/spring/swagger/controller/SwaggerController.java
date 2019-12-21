package com.baeldung.spring.swagger.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baeldung.spring.swagger.model.SwaggerArray;

import io.swagger.annotations.ApiOperation;

public interface SwaggerController {
    @GetMapping("/array")
    List<SwaggerArray> getAll();

    @PostMapping("/array")
    int save(@RequestBody SwaggerArray array);
}
