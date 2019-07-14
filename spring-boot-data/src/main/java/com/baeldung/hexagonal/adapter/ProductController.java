package com.baeldung.hexagonal.adapter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.hexagonal.domain.Product;
import com.baeldung.hexagonal.port.ProductAPIPort;
import com.baeldung.hexagonal.service.ProductService;

/**
 * 
 * @author shwetaJ
 *
 */

@RestController
@RequestMapping("/products/")
public class ProductController implements ProductAPIPort{
	private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	ProductService productService;
	
	@Override
	public ResponseEntity<Product> createProduct(@RequestBody Product product){
		logger.info("Creating Product with obj: " + product);
		
		product = productService.createProduct(product);
		return ResponseEntity.status(HttpStatus.CREATED).body(product);
	}
	
	@Override
	public Product getProduct(@PathVariable int productId){
		logger.info("Returning Product with ID: " + productId);
		return productService.getProduct(productId);
	}
	
	@Override
	public List<Product> getAllProducts(){
		logger.info("Returning All Products");
		return productService.getAllProducts();
	}
}
