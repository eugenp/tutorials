package com.baeldung.object.copy.service;

import java.util.ArrayList;

import com.baeldung.object.copy.domain.ShoppingCart;

public class DeepCloneService implements ObjectCopyService {

	@SuppressWarnings("unchecked")
	public ShoppingCart copy(ShoppingCart shoppingCart, String copyShoppingCartName) {

		ShoppingCart deepCopyShoppingCart = null;

		try {

			deepCopyShoppingCart = (ShoppingCart) shoppingCart.clone();

			ArrayList<String> itemsList = (ArrayList<String>) shoppingCart.getItems();

			deepCopyShoppingCart.setItems((ArrayList<String>) itemsList.clone());

			deepCopyShoppingCart.setCartName(copyShoppingCartName);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return deepCopyShoppingCart;
	}
}
