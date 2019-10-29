package com.baeldung.apache.velocity.service;

import com.baeldung.apache.velocity.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

public class ProductService {

	Logger logger = LoggerFactory.getLogger(ProductService.class);

	public List<Product> getProducts() {
        logger.debug("Product service returning list of products");

		return Arrays.asList(new Product("Laptop", 31000.00), new Product("Mobile", 16000.00),
				new Product("Tablet", 15000.00), new Product("Camera", 23000.00));
	}
}
