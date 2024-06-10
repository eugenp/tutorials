package com.baeldung.object.copy.service;

import java.util.ArrayList;
import java.util.List;

import com.baeldung.object.copy.domain.ShoppingCart;

public class DeepCopyService implements ObjectCopyService {

	public ShoppingCart copy(ShoppingCart sourceShoppingCart, String copyShoppingCartName) {

		ShoppingCart deepCopyShoppingCart = new ShoppingCart();

		List<String> itemList = new ArrayList<String>();

		sourceShoppingCart.setCartName(sourceShoppingCart.getCartName());
		sourceShoppingCart.getItems().stream().forEach(t -> itemList.add(t));

		deepCopyShoppingCart.setItems(itemList);
		
		deepCopyShoppingCart.setCartName(copyShoppingCartName);

		return deepCopyShoppingCart;
	}
}
