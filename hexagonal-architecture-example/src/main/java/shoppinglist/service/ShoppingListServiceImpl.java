package shoppinglist.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import shoppinglist.model.ShoppingList;
import shoppinglist.repository.ShoppingListRepository;

@Service
public class ShoppingListServiceImpl implements ShoppingListService {

	@Autowired
	private ShoppingListRepository shoppingListRepository;

	public void create(String name) {
		ShoppingList shoppingList = new ShoppingList();
		shoppingList.setName(name);
		shoppingList.setProducts(new ArrayList<>());
		shoppingListRepository.create(shoppingList);
	}

	public void addProduct(String name, String product) {
		shoppingListRepository.addProduct(name, product);

	}

	public ShoppingList get(String name) {
		return shoppingListRepository.get(name);
	}

}
