package com.baeldung.adapter;

import com.baeldung.model.Product;
import com.baeldung.port.ProductRestPort;
import com.baeldung.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product/")
public class ProductControllerAdapter implements ProductRestPort {

    @Autowired
    private ProductService productService;

   @Override
    public void create(@RequestBody Product request) {
        productService.create(request.getName(), request.getDescription(), request.getCode());
    }

    @Override
    public Product view(@PathVariable Integer id) {
        return productService.view(id);
    }
}