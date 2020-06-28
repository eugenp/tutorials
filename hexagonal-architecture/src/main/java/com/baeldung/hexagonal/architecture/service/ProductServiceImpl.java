package com.baeldung.hexagonal.architecture.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.hexagonal.architecture.model.Product;
import com.baeldung.hexagonal.architecture.repository.ProductRepository;

/**
 * @author AshwiniKeshri
 *
 */

@Service
public class ProductServiceImpl implements ProductService {

	private Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Autowired
	private ProductRepository productRepository;
	
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	public Product findById(long id) {
		return productRepository.findOne(id);
	}

	public Long create(Product product) {
		product =  productRepository.saveAndFlush(product);
		return product.getId();
	}

	@Override
	public void saveAll(List<Product> products) {
		productRepository.save(products);
	}

}
