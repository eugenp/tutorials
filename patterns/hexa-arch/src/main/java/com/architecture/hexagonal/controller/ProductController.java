package com.architecture.hexagonal.controller;

import com.architecture.hexagonal.component.ProductService;
import com.architecture.hexagonal.model.Product;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping(value = "/{productId:\\d+}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public Product getProductById(
            @PathVariable("productId") final Integer productId) {
        return this.productService.getProduct(productId);
    }

}
