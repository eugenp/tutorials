package com.baeldung.patterns.port;

import java.util.List;

import com.baeldung.patterns.core.domain.Product;

public interface ProductService {

	public void createProduct(Product product);

	public Product getProduct(String name);

	public List<Product> listProduct();

}
