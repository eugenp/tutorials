package com.baeldung.boot.hexagonal.adapter;

import com.baeldung.boot.hexagonal.core.domain.Product;
import com.baeldung.boot.hexagonal.port.ProductService;
import com.baeldung.boot.hexagonal.web.ProductResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController implements ProductResource {
    @Autowired
    private ProductService productService;

    @PostMapping
    public void addProduct(@RequestBody Product product) {
        productService.addProduct(product);
    }

    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable Long productId) {
        return productService.getProduct(productId);
    }

    @GetMapping
    public List<Product> allProducts() {
        return productService.allProducts();
    }
}
