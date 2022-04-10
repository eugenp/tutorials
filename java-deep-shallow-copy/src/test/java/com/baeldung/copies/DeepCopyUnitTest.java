package com.baeldung.copies;

import com.baeldung.models.Flavor;
import com.baeldung.models.Ingredients;
import com.baeldung.models.Smoothie;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class DeepCopyUnitTest {

	@Test
	public void whenDeepCopyCreated_thenFieldChangesAreIndependentFromOriginal() {
		Flavor banana = new Flavor("Banana");
		Ingredients yogurt = new Ingredients("Yogurt");

		Smoothie smoothieA = new Smoothie(banana, yogurt);
		Smoothie smoothieB = null;

		smoothieB = new Smoothie(smoothieA.getFlavor(), smoothieA.getIngredients());
		smoothieB.setFlavor(new Flavor("Tangerine"));

		assertNotEquals(smoothieA.getFlavor(), smoothieB.getFlavor());
	}

}
