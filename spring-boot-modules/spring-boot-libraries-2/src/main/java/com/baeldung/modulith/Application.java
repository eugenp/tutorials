package com.baeldung.modulith;

import com.baeldung.modulith.product.ProductDto;
import com.baeldung.modulith.product.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
@EnableAutoConfiguration
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args)
          .getBean(ProductService.class)
          .create(new ProductDto("baeldung", "course", 10));
    }
}