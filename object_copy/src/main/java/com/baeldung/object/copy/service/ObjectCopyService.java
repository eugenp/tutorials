package com.baeldung.object.copy.service;

import com.baeldung.object.copy.domain.ShoppingCart;

public interface ObjectCopyService {

	ShoppingCart copy(ShoppingCart sourceShoppingCart, String copyShoppingCartName);

}
