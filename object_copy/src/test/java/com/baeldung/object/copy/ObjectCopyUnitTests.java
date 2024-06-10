package com.baeldung.object.copy;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.object.copy.domain.ShoppingCart;
import com.baeldung.object.copy.exception.InvalidCopyTypeException;
import com.baeldung.object.copy.factory.ObjectCopyServiceFactory;
import com.baeldung.object.copy.service.ShoppingCartService;

@SpringBootTest
class ObjectCopyUnitTests {

	@Autowired
	ObjectCopyServiceFactory objectCopyServiceFactory;

	@Autowired
	ShoppingCartService shoppingCartService;

	@Test
	void givenAnObject_whenDoShallowCopy_thenReturnNewReferenceOfObject() throws InvalidCopyTypeException {
		ShoppingCart shoppingCart = createShoppingCart();
		ShoppingCart shallowCopy = objectCopyServiceFactory.getObjectCopyService("shallow").copy(shoppingCart,
				"AfterShallowCopyShoppingCart");

		shallowCopy.getItems().add("Butter");

		Assert.assertTrue(shallowCopy.getCartName().equals(shoppingCart.getCartName()));
		Assert.assertTrue(shoppingCart.getItems().equals(shallowCopy.getItems()));
	}

	@Test
	void givenAnObject_whenDoDeepCopy_thenReturnNewCopyOfObject() throws InvalidCopyTypeException {
		ShoppingCart shoppingCart = createShoppingCart();
		ShoppingCart deepCopy = objectCopyServiceFactory.getObjectCopyService("deep").copy(shoppingCart,
				"AfterShallowCopyShoppingCart");

		deepCopy.setCartName("AfterDeepCopyShoppingCart");
		deepCopy.getItems().add("Cheese");

		Assert.assertFalse(deepCopy.getCartName().equals(shoppingCart.getCartName()));
		Assert.assertFalse(shoppingCart.getItems().equals(deepCopy.getItems()));
	}

	@Test
	void givenAnObject_whenDoCopyByCloning_thenReturnNewCopyOfObject()
			throws CloneNotSupportedException, InvalidCopyTypeException {
		ShoppingCart shoppingCart = createShoppingCart();
		ShoppingCart deepCopyByCloning = objectCopyServiceFactory.getObjectCopyService("clone").copy(shoppingCart,
				"AfterShallowCopyShoppingCart");

		deepCopyByCloning.setCartName("AfterCopyShoppingCart");
		deepCopyByCloning.getItems().add("Apple");

		Assert.assertFalse(deepCopyByCloning.getCartName().equals(shoppingCart.getCartName()));
		Assert.assertTrue(shoppingCart.getItems().equals(deepCopyByCloning.getItems()));
	}

	@Test
	void givenAnObject_whenDoDeepCopyByCloning_thenReturnNewCopyOfObject()
			throws CloneNotSupportedException, InvalidCopyTypeException {
		ShoppingCart shoppingCart = createShoppingCart();
		ShoppingCart deepCopyByCloning = objectCopyServiceFactory.getObjectCopyService("deepclone").copy(shoppingCart,
				"AfterShallowCopyShoppingCart");

		deepCopyByCloning.setCartName("AfterDeepCopyShoppingCart");
		deepCopyByCloning.getItems().add("Apple");

		Assert.assertFalse(deepCopyByCloning.getCartName().equals(shoppingCart.getCartName()));
		Assert.assertFalse(shoppingCart.getItems().equals(deepCopyByCloning.getItems()));
	}

	ShoppingCart createShoppingCart() {
		ShoppingCart shoppingCart = new ShoppingCart();

		List<String> items = new ArrayList<String>();

		items.add("Bread");

		shoppingCart.setItems(items);
		shoppingCart.setCartName("BeforeCopyShoppingCart");

		return shoppingCart;
	}
}
