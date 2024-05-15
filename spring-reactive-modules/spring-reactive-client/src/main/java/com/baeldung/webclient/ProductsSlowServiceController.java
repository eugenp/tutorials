package com.baeldung.webclient;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductsSlowServiceController {

    @GetMapping("/slow-service-products")
    private List<Product> getAllProducts() throws  InterruptedException {
        Thread.sleep(2000L); // delay
        return Arrays.asList(
          new Product("Fancy Smartphone", "A stylish phone you need"),
          new Product("Cool Watch", "The only device you need"),
          new Product("Smart TV", "Cristal clean images")
        );
    }
}