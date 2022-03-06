package com.baeldung.hexagonal.architecture.rest;

import com.baeldung.hexagonal.architecture.domain.Product;
import com.baeldung.hexagonal.architecture.domain.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{productId}")
    public Product getProductById(@PathVariable("productId") Integer productId) {
        return productService.getProductById(productId);
    }

    @PostMapping
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @DeleteMapping("/{productId}")
    public Product removeProduct(@PathVariable("productId") Integer productId) {
        return productService.removeProduct(productId);
    }
}
