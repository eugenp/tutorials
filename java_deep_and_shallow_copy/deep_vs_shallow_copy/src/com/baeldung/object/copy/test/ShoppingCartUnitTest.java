package com.baeldung.object.copy.test;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import com.baeldung.object.copy.ObjectCopyService;
import com.baeldung.object.copy.ShoppingCart;

class ShoppingCartUnitTest {

    @Test
    void givenAnObject_whenDoShallowCopy_thenReturnNewReferenceOfObject() {
        ShoppingCart shoppingCart = getShoppingCart();
        ShoppingCart shallowCopy = getObjectCopyUtils().shallowCopy(shoppingCart, "ShallowCopy");

        shallowCopy.getItems()
            .add("Butter");

        Assert.assertFalse(shallowCopy.getCartName()
            .equals(shoppingCart.getCartName()));
        Assert.assertTrue(shoppingCart.getItems()
            .equals(shallowCopy.getItems()));
    }

    @Test
    void givenAnObject_whenDoDeepCopy_thenReturnNewCopyOfObject() {
        ShoppingCart shoppingCart = getShoppingCart();
        ShoppingCart deepCopy = getObjectCopyUtils().deepCopy(shoppingCart, "DeepCopy");

        deepCopy.getItems()
            .add("Cheese");

        Assert.assertFalse(deepCopy.getCartName()
            .equals(shoppingCart.getCartName()));
        Assert.assertFalse(shoppingCart.getItems()
            .equals(deepCopy.getItems()));
    }
    
    @Test
    void givenAnObject_whenDoCopyByCloning_thenReturnNewCopyOfObject() throws CloneNotSupportedException {
        ShoppingCart shoppingCart = getShoppingCart();
        ShoppingCart deepCopyByCloning = getObjectCopyUtils().cloneCopy(shoppingCart, "CloneCopy");

        deepCopyByCloning.getItems()
            .add("Apple");

        Assert.assertFalse(deepCopyByCloning.getCartName()
            .equals(shoppingCart.getCartName()));
        Assert.assertTrue(shoppingCart.getItems()
            .equals(deepCopyByCloning.getItems()));
    }

    @Test
    void givenAnObject_whenDoDeepCopyByCloning_thenReturnNewCopyOfObject() throws CloneNotSupportedException {
        ShoppingCart shoppingCart = getShoppingCart();
        ShoppingCart deepCopyByCloning = getObjectCopyUtils().deepCloneCopy(shoppingCart, "DeepCloneCopy");

        deepCopyByCloning.getItems()
            .add("Apple");

        Assert.assertFalse(deepCopyByCloning.getCartName()
            .equals(shoppingCart.getCartName()));
        Assert.assertFalse(shoppingCart.getItems()
            .equals(deepCopyByCloning.getItems()));
    }

    ShoppingCart getShoppingCart() {
        ShoppingCart shoppingCart = new ShoppingCart();

        List<String> items = new ArrayList<String>();

        items.add("Bread");

        shoppingCart.setItems(items);
        shoppingCart.setCartName("BeforeCopyShoppingCart");

        return shoppingCart;
    }

    ObjectCopyService getObjectCopyUtils() {
        return new ObjectCopyService();
    }

}
