package com.baeldung.copy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import com.baeldung.copy.model.Item;
import com.baeldung.copy.model.Shop;

class JavaCopyApplicationTests {


    //Use the appropriate @clone() implementation of Shop class for this test to pass.
    @Test
    void whenShallowCopy_thenSuccessful() throws CloneNotSupportedException {
        Item item = new Item("mac", 15);
        Shop s1 = new Shop("shop-1", item);
        Shop s2 = (Shop) s1.clone();

        item.setPrice(20);
        Assertions.assertEquals(s1.getName(), s2.getName());
        Assertions.assertEquals(s1.getItem(), s2.getItem());
        Assertions.assertEquals(s1.getItem().getPrice(), s2.getItem().getPrice());
    }

    //Use the appropriate @clone() implementation of Shop class for this test to pass.
/*
    @Test
    void whenDeepCopyWithCloneable_thenSuccessful() throws CloneNotSupportedException {
        Item item = new Item("mac", 15);
        Shop s1 = new Shop("shop-1", item);
        Shop s2 = (Shop) s1.clone();

        item.setPrice(20);
        Assertions.assertEquals(s1.getName(), s2.getName());
        Assertions.assertNotEquals(s1.getItem(), s2.getItem());
        Assertions.assertNotEquals(s1.getItem().getPrice(), s2.getItem().getPrice());

    }
 */

    @Test
    void whenDeepCopyWithCopyConstructor_thenSuccessful() {
        Item item = new Item("mac", 15);
        Shop s1 = new Shop("shop-1", item);
        Shop s2 = new Shop(s1);

        Assertions.assertEquals(s1.getName(), s2.getName());
        Assertions.assertNotEquals(s1.getItem(), s2.getItem());
    }

}
