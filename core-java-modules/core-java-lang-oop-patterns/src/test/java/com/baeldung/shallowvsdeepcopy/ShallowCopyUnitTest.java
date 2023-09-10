package com.baeldung.shallowvsdeepcopy;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ShallowCopyUnitTest {

	@Test
	public void whenOriginalModifiedAfterCopy_thenCopyAlsoModified() {
		Recipe original = new Recipe("Smoothie");
		original.addIngredient("Yoghurt");
		original.addIngredient("Milk");
		original.addIngredient("Banana");
		Recipe copy = Recipe.shallow(original);
		copy.addIngredient("Strawberries");
		original.addIngredient("Peanut Butter");
		
		assertTrue(copy.getIngredients().contains("Peanut Butter"));
	}
}