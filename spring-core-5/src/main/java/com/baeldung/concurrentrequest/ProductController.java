package com.baeldung.concurrentrequest;

import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/{id}")
    public Product getProductDetails(@PathVariable("id") int productId) {

        System.out.println(Thread.currentThread().getName() + " - getProductDetails: " + productId + " -" + new Date());

        return productService.getProductById(productId)
            .orElse(null);
    }

    @GetMapping("{id}/stock")
    public Stock getProductStock(@PathVariable("id") int productId) {

        System.out.println(Thread.currentThread().getName() + " - getProductStock: " + productId + " -" + new Date());

        return productService.getProductById(productId)
            .map(Product::getStock)
            .orElse(null);
    }
}
