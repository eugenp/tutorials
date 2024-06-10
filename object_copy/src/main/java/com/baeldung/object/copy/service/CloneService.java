package com.baeldung.object.copy.service;

import com.baeldung.object.copy.domain.ShoppingCart;

public class CloneService implements ObjectCopyService {

	public ShoppingCart copy(ShoppingCart shoppingCart, String copyShoppingCartName) {

		ShoppingCart deepCopyShoppingCart = null;

		try {

			deepCopyShoppingCart = (ShoppingCart) shoppingCart.clone();
			
			deepCopyShoppingCart.setCartName(copyShoppingCartName);

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return deepCopyShoppingCart;
	}
}
