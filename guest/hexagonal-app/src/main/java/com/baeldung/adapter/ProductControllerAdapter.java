package com.baeldung.adapter;

import com.baeldung.domain.Product;
import com.baeldung.port.ProductUIPort;
import com.baeldung.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employees/")
public class ProductControllerAdapter implements ProductUIPort{

    @Autowired
    private ProductService employeeService;

   @Override
    public void create(@RequestBody Product request) {
        employeeService.create(request.getProductName(), request.getDesc(), request.getPrice());
    }

    @Override
    public Product view(@PathVariable Integer id) {
        Product employee = employeeService.view(id);
        return employee;
    }
}