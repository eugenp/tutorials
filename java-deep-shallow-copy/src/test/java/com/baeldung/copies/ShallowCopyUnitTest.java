package com.baeldung.copies;

import com.baeldung.models.Flavor;
import com.baeldung.models.Ingredients;
import com.baeldung.models.Smoothie;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShallowCopyUnitTest {

	@Test
	public void whenShallowCopyCreated_thenFieldChangesReflectOnOriginal() {
		Flavor flavor = new Flavor("Apple");
		Ingredients ingredients = new Ingredients("Yogurt");

		Smoothie smoothieA = new Smoothie(flavor, ingredients);
		Smoothie smoothieB = null;

		smoothieB = smoothieA;
		smoothieB.setFlavor(new Flavor("Peach"));

		assertEquals(smoothieA.getFlavor(), smoothieB.getFlavor());
	}
}
