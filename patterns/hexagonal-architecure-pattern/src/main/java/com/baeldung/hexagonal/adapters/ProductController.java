package com.baeldung.hexagonal.adapters;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.domain.Product;
import com.baeldung.hexagonal.ports.IProductService;

/**
 * Primary Adapter - Connecting to ProductService via IProductController Port
 * 
 * @author bhargavakotharu
 *
 */
@RestController
@RequestMapping("/products")
public class ProductController {

	@Autowired
	private IProductService productService;

	@GetMapping("/{id}")
	public ResponseEntity<Product> getProductById(@PathVariable(name = "id") Integer id) {

		return ResponseEntity.ok().body(productService.getProduct(id));
	}

	@GetMapping
	public ResponseEntity<List<Product>> getProducts() {

		return ResponseEntity.ok().body(productService.getProducts());
	}

	@PostMapping
	public ResponseEntity<?> createProduct(@RequestBody Product product) {

		productService.createProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
