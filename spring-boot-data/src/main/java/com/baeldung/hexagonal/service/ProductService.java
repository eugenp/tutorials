package com.baeldung.hexagonal.service;

import java.util.List;

import com.baeldung.hexagonal.domain.Product;

/**
 * 
 * @author shwetaJ
 *
 */

public interface ProductService {
	
	Product createProduct(Product product);
	Product getProduct(int productId);
	List<Product> getAllProducts();
}
