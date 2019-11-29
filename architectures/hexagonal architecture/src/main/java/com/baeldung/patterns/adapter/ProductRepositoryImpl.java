package com.baeldung.patterns.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import com.baeldung.patterns.core.domain.Product;
import com.baeldung.patterns.port.ProductRepository;

@Repository
public class ProductRepositoryImpl implements ProductRepository {

	private Map<String, Product> product = new HashMap<String, Product>();

	@Override
	public void createProduct(Product product) {
		this.product.put(product.getName(), product);
	}

	@Override
	public Product getProduct(String name) {
		return product.get(name);
	}

	@Override
	public List<Product> getAllProduct() {
		return product.values().stream().collect(Collectors.toList());
	}

}
