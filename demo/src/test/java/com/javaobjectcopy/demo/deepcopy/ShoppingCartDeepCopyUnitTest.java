package com.javaobjectcopy.demo.deepcopy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ShoppingCartDeepCopyUnitTest {

    @Test
    void whenDeepClonedObjectIsUpdated_thenChangeShouldNotReflectInOriginal() throws CloneNotSupportedException {
        ShoppingCartDeepCopy original = new ShoppingCartDeepCopy(2, new Item2("soap"));

        ShoppingCartDeepCopy cloned = (ShoppingCartDeepCopy) original.clone();

        Assertions.assertNotNull(cloned);
        Assertions.assertEquals(original.getItem().getName(), cloned.getItem().getName(), "After Cloning:\nItem name should be equal");
        Assertions.assertEquals(original.getNumOfItems(), cloned.getNumOfItems(), "NumOfItems should be equal");

        cloned.getItem().setName("bag");
        cloned.setNumOfItems(4);

        Assertions.assertNotEquals(original.getItem().getName(), cloned.getItem().getName(), "After Updating data:\nItem name should not be equal");
        Assertions.assertNotEquals(original.getNumOfItems(), cloned.getNumOfItems(), "NumOfItems should not be equal");
    }

    @Test
    void whenDeepClonedUsingCopyConstructorObjectIsUpdated_thenChangeShouldNotReflectInOriginal() {
        ShoppingCartDeepCopy original = new ShoppingCartDeepCopy(2, new Item2("soap"));

        ShoppingCartDeepCopy cloned = new ShoppingCartDeepCopy(original);

        Assertions.assertNotNull(cloned);
        Assertions.assertEquals(original.getItem().getName(), cloned.getItem().getName(), "After Cloning:\nItem name should be equal");
        Assertions.assertEquals(original.getNumOfItems(), cloned.getNumOfItems(), "NumOfItems should be equal");

        cloned.getItem().setName("bag");
        cloned.setNumOfItems(4);

        Assertions.assertNotEquals(original.getItem().getName(), cloned.getItem().getName(), "After Updating data:\nItem name should not be equal");
        Assertions.assertNotEquals(original.getNumOfItems(), cloned.getNumOfItems(), "NumOfItems should not be equal");
    }
}
