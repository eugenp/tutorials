package com.baeldung.hexagonal.ports;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.baeldung.hexagonal.domain.Product;

/**
 * Inbound Port which exposes ProductService to it's consumers
 * 
 * @author bhargavakotharu
 *
 */
public interface IProductController {

	public ResponseEntity<Product> getProductById(Integer id);

	public ResponseEntity<List<Product>> getProducts();

	public ResponseEntity<?> createProduct(Product product);
}
