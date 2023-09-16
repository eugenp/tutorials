package com.baeldung.shallowvsdeepcopy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

public class DeepCopyUnitTest {

    @Test
    public void whenOriginalModifiedAfterCopy_thenCopyUnaffected() {
        Recipe original = new Recipe("Smoothie");
        original.addIngredient("Yoghurt");
        original.addIngredient("Milk");
        original.addIngredient("Banana");
        Recipe copy = Recipe.deep(original);
        copy.addIngredient("Strawberries");
        original.addIngredient("Peanut Butter");

        assertNotEquals(original.getIngredients(), copy.getIngredients());
        assertEquals(4, copy.getIngredients().size());
        assertEquals("Yoghurt", copy.getIngredients().get(0));
        assertEquals("Milk", copy.getIngredients().get(1));
        assertEquals("Banana", copy.getIngredients().get(2));
        assertEquals("Strawberries", copy.getIngredients().get(3));
    }
}