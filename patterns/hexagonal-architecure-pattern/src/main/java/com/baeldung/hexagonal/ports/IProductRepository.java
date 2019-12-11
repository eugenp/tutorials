package com.baeldung.hexagonal.ports;

import java.util.List;

import com.baeldung.hexagonal.domain.Product;

/**
 * Outbound Port
 * 
 * @author bhargavakotharu
 *
 */
public interface IProductRepository {

	public Product getProductById(Integer id);

	public List<Product> getProducts();

	public void createProduct(Product product);
}
