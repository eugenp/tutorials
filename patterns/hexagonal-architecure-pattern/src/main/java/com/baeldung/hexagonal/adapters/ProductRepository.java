package com.baeldung.hexagonal.adapters;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.baeldung.hexagonal.domain.Product;
import com.baeldung.hexagonal.ports.IProductRepository;

/**
 * Secondary Adapter - connects to data store (in this case it's static
 * in-memory list, but this can be an external database)
 * 
 * @author bhargavakotharu
 *
 */
@Repository
public class ProductRepository implements IProductRepository {

	public static List<Product> productsList = new ArrayList<Product>();

	@Override
	public Product getProductById(Integer id) {

		return productsList.stream().filter(p -> p.getId().equals(id)).findFirst().get();
	}

	@Override
	public List<Product> getProducts() {

		return productsList;
	}

	@Override
	public void createProduct(Product product) {

		productsList.add(product);
	}

	/**
	 * Initializing the application with some data.
	 */
	@PostConstruct
	public static void init() {
		productsList.add(new Product(1, "BMW", "Car", "Premium Car Brand"));
		productsList.add(new Product(2, "Apple", "Mobile", "Best Mobile Phone Brand"));
		productsList.add(new Product(3, "Banana", "Fruit", "Source of Carbohydrates"));
		productsList.add(new Product(4, "Mango", "Fruit", "Very Sweet"));
		productsList.add(new Product(5, "Ferrari", "Car", "Premium Sports Car Brand"));
	}

}
