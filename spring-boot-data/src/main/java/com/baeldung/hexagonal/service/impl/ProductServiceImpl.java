package com.baeldung.hexagonal.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.domain.Product;
import com.baeldung.hexagonal.port.ProductRepositoryPort;
import com.baeldung.hexagonal.service.ProductService;

/**
 * 
 * @author shwetaJ
 *
 */

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepositoryPort productRepository;
	
	@Override
	public Product createProduct(Product product) {
		// Business validation and Business logic goes here
		return productRepository.createProduct(product);
	}

	@Override
	public Product getProduct(int productId) {
		return productRepository.getProduct(productId);
	}

	@Override
	public List<Product> getAllProducts() {
		return productRepository.getAllProducts();
	}
}
