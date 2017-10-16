package com.baeldung.dependency_injection_types;

import org.springframework.boot.Banner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication(scanBasePackages = "com.baeldung.dependency_injection_types")
public class ProductCatalogApplication {

    public static void main(String[] args) {
        SpringApplicationBuilder applicationBuilder = new SpringApplicationBuilder(ProductCatalogApplication.class);
        applicationBuilder.bannerMode(Banner.Mode.OFF);
        applicationBuilder.run(args);
    }
}
