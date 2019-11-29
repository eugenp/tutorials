package com.baeldung.patterns.adapter;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.patterns.core.domain.Product;
import com.baeldung.patterns.port.ProductService;
import com.baeldung.patterns.web.ProductRestUI;

@RestController
@RequestMapping("/product")
public class ProductController implements ProductRestUI {

	@Autowired
	private ProductService productService;

	@Override
	public void createProduct(@RequestBody Product product) {
		productService.createProduct(product);
	}

	@Override
	public Product getProduct(@PathVariable String name) {
		return productService.getProduct(name);
	}

	@Override
	public List<Product> listProduct() {
		return productService.listProduct();
	}
}
