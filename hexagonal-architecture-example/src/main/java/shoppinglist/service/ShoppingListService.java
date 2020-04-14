package shoppinglist.service;

import shoppinglist.model.ShoppingList;

public interface ShoppingListService {
	
	void create(String name);
	
	void addProduct(String name, String product);
	
	ShoppingList get(String name);
	
}
