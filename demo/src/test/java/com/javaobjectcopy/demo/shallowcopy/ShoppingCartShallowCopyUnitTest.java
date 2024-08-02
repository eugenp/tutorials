package com.javaobjectcopy.demo.shallowcopy;

import com.javaobjectcopy.demo.deepcopy.Item1;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ShoppingCartShallowCopyUnitTest {

    @Test
    void whenShallowClonedObjectIsUpdated_thenChangeShouldReflectInOriginal() throws CloneNotSupportedException {
        ShoppingCartShallowCopy original = new ShoppingCartShallowCopy(2, new Item1("soap"));

        ShoppingCartShallowCopy cloned = (ShoppingCartShallowCopy) original.clone();

        Assertions.assertNotNull(cloned);
        Assertions.assertEquals(original.getItem().getName(), cloned.getItem().getName(), "After Cloning:\nItem name should be equal");
        Assertions.assertEquals(original.getNumOfItems(), cloned.getNumOfItems(), "NumOfItems should be equal");

        cloned.getItem().setName("bag");
        cloned.setNumOfItems(3);

        Assertions.assertEquals(original.getItem().getName(), cloned.getItem().getName(), "After Updating data:\nItem name should be equal");
        Assertions.assertNotEquals(original.getNumOfItems(), cloned.getNumOfItems(), "NumOfItems should be equal");
    }
}