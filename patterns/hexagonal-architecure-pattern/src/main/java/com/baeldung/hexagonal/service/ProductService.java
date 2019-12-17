package com.baeldung.hexagonal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.domain.Product;
import com.baeldung.hexagonal.ports.IProductRepository;
import com.baeldung.hexagonal.ports.IProductService;

@Service
public class ProductService implements IProductService {

	@Autowired
	private IProductRepository repository;

	@Override
	public Product getProduct(Integer id) {

		return repository.getProductById(id);
	}

	@Override
	public List<Product> getProducts() {

		return repository.getProducts();
	}

	@Override
	public void createProduct(Product product) {

		repository.createProduct(product);
	}

}
