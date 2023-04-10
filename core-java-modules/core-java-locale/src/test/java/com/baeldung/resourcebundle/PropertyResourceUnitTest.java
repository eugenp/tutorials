package com.baeldung.resourcebundle;

import org.junit.Test;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PropertyResourceUnitTest {

    @Test
    public void givenLocaleUsAsDefualt_whenGetBundleForLocalePlPl_thenItShouldContain3ButtonsAnd1Label() {
        Locale locale = Locale.getDefault();
    	Locale.setDefault(Locale.US);

        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundle.resource", new Locale("pl", "PL"));

        assertTrue(bundle.keySet()
            .containsAll(Arrays.asList("backButton", "helloLabel", "cancelButton", "continueButton", "helloLabelNoEncoding")));
        Locale.setDefault(locale);
    }

    @Test
    public void givenLocaleUsAsDefualt_whenGetBundleForLocaleFrFr_thenItShouldContainKeys1To3AndKey4() {
        Locale locale = Locale.getDefault();

    	Locale.setDefault(Locale.US);

        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundle.resource", new Locale("fr", "FR"));

        assertTrue(bundle.keySet()
            .containsAll(Arrays.asList("deleteButton", "helloLabel", "cancelButton", "continueButton")));
        Locale.setDefault(locale);
    }

    @Test
    public void givenLocaleChinaAsDefualt_whenGetBundleForLocaleFrFr_thenItShouldOnlyContainKeys1To3() {
        Locale locale = Locale.getDefault();

    	Locale.setDefault(Locale.CHINA);

        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundle.resource", new Locale("fr", "FR"));

        assertTrue(bundle.keySet()
            .containsAll(Arrays.asList("continueButton", "helloLabel", "cancelButton")));
        Locale.setDefault(locale);
    }

    @Test
    public void givenLocaleChinaAsDefualt_whenGetBundleForLocaleFrFrAndExampleControl_thenItShouldOnlyContainKey5() {
        Locale locale = Locale.getDefault();

    	Locale.setDefault(Locale.CHINA);

        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundle.resource", new Locale("fr", "FR"), new ExampleControl());

        assertTrue(bundle.keySet()
            .containsAll(Arrays.asList("backButton", "helloLabel")));
        Locale.setDefault(locale);
    }

    @Test
    public void givenValuesDifferentlyEncoded_whenGetBundleForLocalePlPl_thenItShouldContain3ButtonsAnd1Label() {
        Locale locale = Locale.getDefault();
        System.out.println(Locale.getDefault());
        System.out.println("file.encoding=" + System.getProperty("file.encoding"));

    	ResourceBundle bundle = ResourceBundle.getBundle("resourcebundle.resource", new Locale("pl", "PL"));

        assertEquals(bundle.getString("helloLabel"), "cześć");
        
        // this depends on the local system encoding
        //assertEquals(bundle.getString("helloLabelNoEncoding"), "czeÅ\u009BÄ\u0087");
        Locale.setDefault(locale);
    }

}
