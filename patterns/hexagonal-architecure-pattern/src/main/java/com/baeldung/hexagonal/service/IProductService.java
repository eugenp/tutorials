package com.baeldung.hexagonal.service;

import java.util.List;

import com.baeldung.hexagonal.domain.Product;

/**
 * Core Business Logic exposed through a service.
 * 
 * @author bhargavakotharu
 *
 */
public interface IProductService {

	public Product getProduct(Integer id);

	public List<Product> getProducts();

	public void createProduct(Product product);
}
