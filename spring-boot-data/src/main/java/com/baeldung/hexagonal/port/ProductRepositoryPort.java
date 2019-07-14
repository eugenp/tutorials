package com.baeldung.hexagonal.port;

import java.util.List;

import com.baeldung.hexagonal.domain.Product;

/**
 * 
 * @author shwetaJ
 *
 */

public interface ProductRepositoryPort {

	Product createProduct(Product product);
	Product getProduct(int productId);
	List<Product> getAllProducts();	
}
