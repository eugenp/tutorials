package com.baeldung.hexagonalarchitecture.controller;

import com.baeldung.hexagonalarchitecture.dto.ProductDto;
import com.baeldung.hexagonalarchitecture.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;
    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @GetMapping("/{product-id}")
    public ProductDto getProduct(@PathVariable("product-id") UUID productId){
        return productService.getProduct(productId);
    }

    @PostMapping
    public void addProduct(ProductDto productDto){
        productService.addProduct(productDto);
    }

}
