package com.baeldung.spring.swagger.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.spring.swagger.model.SwaggerArray;
import com.baeldung.spring.swagger.service.Service;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
public class ArraySwaggerController implements SwaggerController {
    @Autowired
    Service swaggerArrayService;

    @GetMapping("/array")
    public List<SwaggerArray> getAll() {
        // log.info("Get all Arrays");
        return swaggerArrayService.getAll();
    }

    @PostMapping("/array")
    @ApiOperation(value = "Inserting array example")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "array", value = "List of strings", paramType = "body",
            dataType="SwaggerArray")})
    public int save(@ApiParam(required = true) @RequestBody SwaggerArray array) {

        swaggerArrayService.save(array);
        return array.getId();
    }
}
