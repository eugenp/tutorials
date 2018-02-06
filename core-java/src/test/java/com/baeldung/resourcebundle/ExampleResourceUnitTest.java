package com.baeldung.resourcebundle;

import org.junit.Test;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExampleResourceUnitTest {

    @Test
    public void whenGetBundleExampleResource_thenItShouldContainKeyAAndValueA() {
        ResourceBundle exampleBundle = ResourceBundle.getBundle("com.baeldung.resourcebundle.ExampleResource");

        assertEquals(exampleBundle.getString("keyA"), "valueA");
    }

    @Test
    public void whenGetBundleExampleResourceForLocalePlPl_thenItShouldInheritKeyAKeyBKeyCKeyD() {
        Locale plLocale = new Locale("pl", "PL");

        ResourceBundle exampleBundle = ResourceBundle.getBundle("com.baeldung.resourcebundle.ExampleResource", plLocale);

        assertTrue(exampleBundle.keySet().containsAll(Arrays.asList("keyA", "keyB", "keyC", "keyD")));
        assertEquals(exampleBundle.getString("keyA"), "PL_pl_valueA");
        assertEquals(exampleBundle.getString("keyB"), "pl_valueB");
        assertEquals(exampleBundle.getString("keyC"), "valueC");
        assertEquals(exampleBundle.getObject("keyD"), new Double(44.44));
    }

    @Test
    public void whenGetBundleExampleResourceForLocaleUs_thenItShouldContainKeyAKeyBAndNotContainKeyCKeyD() {
        Locale usLocale = Locale.US;

        ResourceBundle exampleBundle = ResourceBundle.getBundle("com.baeldung.resourcebundle.ExampleResource", usLocale);

        assertFalse(exampleBundle.keySet().containsAll(Arrays.asList("keyA", "keyB", "keyC", "keyD")));
        assertTrue(exampleBundle.keySet().containsAll(Arrays.asList("keyA", "keyB")));
        assertEquals(exampleBundle.getString("keyA"), "valueA");
        assertEquals(exampleBundle.getString("keyB"), "valueB");
    }

}
