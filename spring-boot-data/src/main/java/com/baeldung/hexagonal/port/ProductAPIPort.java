package com.baeldung.hexagonal.port;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baeldung.hexagonal.domain.Product;

/**
 * 
 * @author shwetaJ
 *
 */
public interface ProductAPIPort {

	@PostMapping
	ResponseEntity<Product> createProduct(@RequestBody Product product);
	
	@GetMapping("/{productId}")
	Product getProduct(@PathVariable int productId);
	
	@GetMapping
	List<Product> getAllProducts();
	
}
