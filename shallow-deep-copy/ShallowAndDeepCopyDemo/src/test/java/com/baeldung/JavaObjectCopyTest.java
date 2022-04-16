package com.baeldung;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class JavaObjectCopyTest {

    @Test
    public void shallowCopyTest() {
        Item shirt = new Item();
        shirt.setId("1");
        shirt.setName("Shirt");
        shirt.setPrice(350.0);

        Item trouser = new Item();
        shirt.setId("2");
        shirt.setName("Trouser");
        shirt.setPrice(500.0);

        ShoppingCart shoppingCart1 = new ShoppingCart();
        shoppingCart1.setId(1);
        shoppingCart1.setShoppingItems(Arrays.asList(shirt, trouser));

        //Creating shallow copy of shoppingCart1
        ShoppingCart shoppingCart2 = shoppingCart1;
        assertTrue(shoppingCart1 == shoppingCart2);
        assertEquals(shoppingCart1.getShoppingItems(), shoppingCart2.getShoppingItems());

        //Change in the original object should also affect the copy of an object
        shoppingCart1.setId(2);
        assertEquals(2, shoppingCart2.getId());
    }

    @Test
    public void deepCopyTest() {
        Item shirt = new Item();
        shirt.setId("1");
        shirt.setName("Shirt");
        shirt.setPrice(350.0);

        Item trouser = new Item();
        shirt.setId("2");
        shirt.setName("Trouser");
        shirt.setPrice(500.0);

        List<Item> itemList = Arrays.asList(shirt, trouser);
        ShoppingCart shoppingCart1 = new ShoppingCart();
        shoppingCart1.setId(1);
        shoppingCart1.setShoppingItems(itemList);

        //Creating deep copy of shoppingCart2
        ShoppingCart shoppingCart2 = new ShoppingCart();
        shoppingCart2.setId(shoppingCart1.getId());
        shoppingCart2.setShoppingItems(Arrays.asList(new Item(itemList.get(0)
            .getId(), itemList.get(0)
            .getName(), itemList.get(0)
            .getPrice()), new Item(itemList.get(0)
            .getId(), itemList.get(1)
            .getName(), itemList.get(1)
            .getPrice())));

        assertFalse(shoppingCart1 == shoppingCart2);
        assertNotEquals(shoppingCart1.getShoppingItems(), shoppingCart2.getShoppingItems());

        //Change in the original object should not affect the copy of an object
        shoppingCart1.setId(2);
        assertEquals(1, shoppingCart2.getId());

    }
}
