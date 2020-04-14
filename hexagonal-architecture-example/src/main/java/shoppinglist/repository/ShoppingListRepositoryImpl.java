package shoppinglist.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import shoppinglist.model.ShoppingList;

@Repository
public class ShoppingListRepositoryImpl implements ShoppingListRepository {

	private Map<String, ShoppingList> shoppingLists = new HashMap<>();

	public void create(ShoppingList shoppingList) {
		shoppingLists.putIfAbsent(shoppingList.getName(), shoppingList);
	}

	public void addProduct(String name, String product) {
		Optional.ofNullable(shoppingLists.get(name)).orElseThrow().addProduct(product);
	}

	public ShoppingList get(String name) {
		return shoppingLists.get(name);
	}

}
