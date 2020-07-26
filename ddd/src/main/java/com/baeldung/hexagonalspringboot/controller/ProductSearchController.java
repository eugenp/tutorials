package com.baeldung.hexagonalspringboot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonalspringboot.domain.Product;
import com.baeldung.hexagonalspringboot.port.IProductSearchService;

@RestController
@RequestMapping("/products")
public class ProductSearchController {

    @Autowired
    private IProductSearchService productSearchService;

    @GetMapping
    public List<Product> getproducts() {
        return productSearchService.getAllproducts();

    }

    @GetMapping("/{category}")
    public List<Product> getproducts(@PathVariable String category) {
        return productSearchService.getproductsByCategory(category);

    }
}
