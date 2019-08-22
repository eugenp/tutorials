package com.baeldung.hexagonal.springapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.baeldung.hexagonal.core.CartRepository;
import com.baeldung.hexagonal.core.CouponRepository;
import com.baeldung.hexagonal.core.LoggedInCustomerHolder;
import com.baeldung.hexagonal.core.ProductRepository;
import com.baeldung.hexagonal.core.ShoppingCartService;
import com.baeldung.hexagonal.springapp.repository.JpaProductRepository;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = JpaProductRepository.class)
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public ShoppingCartService shoppingCartService(ProductRepository productRepository,
            LoggedInCustomerHolder customerHolder, CartRepository cartRepository,
            CouponRepository couponRepository) {
	    return new ShoppingCartService(productRepository, customerHolder, cartRepository, couponRepository);
    }

}
