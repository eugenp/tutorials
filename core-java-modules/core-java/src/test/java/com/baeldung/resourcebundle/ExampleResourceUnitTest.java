package com.baeldung.resourcebundle;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.*;

public class ExampleResourceUnitTest {

    @Test
    public void whenGetBundleExampleResourceForLocalePlPl_thenItShouldInheritPropertiesGreetingAndLanguage() {
        Locale plLocale = new Locale("pl", "PL");

        ResourceBundle exampleBundle = ResourceBundle.getBundle("com.baeldung.resourcebundle.ExampleResource", plLocale);

        assertTrue(exampleBundle.keySet()
            .containsAll(Arrays.asList("toUsdRate", "cities", "greeting", "currency", "language")));
        assertEquals(exampleBundle.getString("greeting"), "cześć");
        assertEquals(exampleBundle.getObject("toUsdRate"), new BigDecimal("3.401"));
        assertArrayEquals(exampleBundle.getStringArray("cities"), new String[] { "Warsaw", "Cracow" });
    }

    @Test
    public void whenGetBundleExampleResourceForLocaleUs_thenItShouldContainOnlyGreeting() {
        Locale usLocale = Locale.US;

        ResourceBundle exampleBundle = ResourceBundle.getBundle("com.baeldung.resourcebundle.ExampleResource", usLocale);

        assertFalse(exampleBundle.keySet()
            .containsAll(Arrays.asList("toUsdRate", "cities", "currency", "language")));
        assertTrue(exampleBundle.keySet()
            .containsAll(Arrays.asList("greeting")));
    }

}
