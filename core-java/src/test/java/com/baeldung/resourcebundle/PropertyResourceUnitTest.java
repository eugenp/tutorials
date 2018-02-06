package com.baeldung.resourcebundle;

import org.junit.Test;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertTrue;

public class PropertyResourceUnitTest {

    @Test
    public void givenLocaleUsAsDefualt_whenGetBundleForLocalePlPl_thenItShouldContainKeys1To3AndKey5() {
        Locale.setDefault(Locale.US);

        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundle.resource", new Locale("pl", "PL"));

        assertTrue(bundle.keySet().containsAll(Arrays.asList("key1", "key2", "key3", "key5")));
    }

    @Test
    public void givenLocaleUsAsDefualt_whenGetBundleForLocaleFrFr_thenItShouldContainKeys1To3AndKey4() {
        Locale.setDefault(Locale.US);

        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundle.resource", new Locale("fr", "FR"));

        assertTrue(bundle.keySet().containsAll(Arrays.asList("key1", "key2", "key3", "key4")));
    }

    @Test
    public void givenLocaleChinaAsDefualt_whenGetBundleForLocaleFrFr_thenItShouldOnlyContainKeys1To3() {
        Locale.setDefault(Locale.CHINA);

        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundle.resource", new Locale("fr", "FR"));

        assertTrue(bundle.keySet().containsAll(Arrays.asList("key1", "key2", "key3")));
    }

    @Test
    public void givenLocaleChinaAsDefualt_whenGetBundleForLocaleFrFrAndExampleControl_thenItShouldOnlyContainKey5() {
        Locale.setDefault(Locale.CHINA);

        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundle.resource", new Locale("fr", "FR"), new ExampleControl());

        assertTrue(bundle.keySet().containsAll(Arrays.asList("key5")));
    }
}
