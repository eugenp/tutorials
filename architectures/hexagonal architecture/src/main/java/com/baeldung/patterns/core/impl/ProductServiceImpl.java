package com.baeldung.patterns.core.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.patterns.core.domain.Product;
import com.baeldung.patterns.port.ProductRepository;
import com.baeldung.patterns.port.ProductService;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public void createProduct(Product product) {
		productRepository.createProduct(product);
	}

	@Override
	public Product getProduct(String name) {

		return productRepository.getProduct(name);
	}

	@Override
	public List<Product> listProduct() {
		return productRepository.getAllProduct();
	}

}
