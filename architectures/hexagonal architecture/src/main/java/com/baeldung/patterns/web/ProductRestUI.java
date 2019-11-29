package com.baeldung.patterns.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baeldung.patterns.core.domain.Product;

public interface ProductRestUI {

	@PostMapping
	void createProduct(@RequestBody Product product);

	@GetMapping("/{name}")
	public Product getProduct(@PathVariable String name);

	@GetMapping
	public List<Product> listProduct();

}
