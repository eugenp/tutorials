package shoppinglist.repository;

import shoppinglist.model.ShoppingList;

public interface ShoppingListRepository {

	void create(ShoppingList shoppingList);
	
	void addProduct(String name, String product);
	
	ShoppingList get(String name);
}
