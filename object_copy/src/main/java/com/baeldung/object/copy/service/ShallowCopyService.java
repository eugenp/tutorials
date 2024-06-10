package com.baeldung.object.copy.service;

import com.baeldung.object.copy.domain.ShoppingCart;

public class ShallowCopyService implements ObjectCopyService {

	public ShoppingCart copy(ShoppingCart sourceShoppingCart, String copyShoppingCartName) {

		ShoppingCart shallowCopyShoppingCart = sourceShoppingCart;

		shallowCopyShoppingCart.setCartName(copyShoppingCartName);

		return shallowCopyShoppingCart;
	}
}
