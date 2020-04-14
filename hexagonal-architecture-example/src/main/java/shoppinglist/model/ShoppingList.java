package shoppinglist.model;

import java.util.Collections;
import java.util.List;

public class ShoppingList {

	private String name;

	private List<String> products;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getProducts() {
		return Collections.unmodifiableList(products);
	}

	public void setProducts(List<String> products) {
		this.products = products;
	}

	public void addProduct(String product) {
		products.add(product);
	}

}
