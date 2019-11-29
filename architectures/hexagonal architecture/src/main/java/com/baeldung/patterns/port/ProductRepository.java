package com.baeldung.patterns.port;

import java.util.List;

import com.baeldung.patterns.core.domain.Product;

public interface ProductRepository {

	void createProduct(Product product);

	Product getProduct(String name);

	List<Product> getAllProduct();

}
