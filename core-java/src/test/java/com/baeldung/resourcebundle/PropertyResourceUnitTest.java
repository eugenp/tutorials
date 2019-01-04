package com.baeldung.resourcebundle;

import org.junit.Test;

import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * 测试：资源包加载
 * 注意：去加载resources路径下的文件
 * 比如：resourcebundle.resource，是加载resources/resourcebundle路径下以resource开头并且符合规则的资源。
 */
public class PropertyResourceUnitTest {

    @Test
    public void givenLocaleUsAsDefualt_whenGetBundleForLocalePlPl_thenItShouldContain3ButtonsAnd1Label() {
        Locale.setDefault(Locale.US);

        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundle.resource", new Locale("pl", "PL"));

        Set<String> keySet = bundle.keySet();
        System.out.println("keySet:{}" + keySet);

        assertTrue(bundle.keySet()
            .containsAll(Arrays.asList("backButton", "helloLabel", "cancelButton", "continueButton", "helloLabelNoEncoding")));
    }

    @Test
    public void givenLocaleUsAsDefualt_whenGetBundleForLocaleFrFr_thenItShouldContainKeys1To3AndKey4() {
        Locale.setDefault(Locale.US);

        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundle.resource", new Locale("fr", "FR"));

        assertTrue(bundle.keySet()
            .containsAll(Arrays.asList("deleteButton", "helloLabel", "cancelButton", "continueButton")));
    }

    @Test
    public void givenLocaleChinaAsDefualt_whenGetBundleForLocaleFrFr_thenItShouldOnlyContainKeys1To3() {
        Locale.setDefault(Locale.CHINA);

        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundle.resource", new Locale("fr", "FR"));

        assertTrue(bundle.keySet()
            .containsAll(Arrays.asList("continueButton", "helloLabel", "cancelButton")));
    }

    @Test
    public void givenLocaleChinaAsDefualt_whenGetBundleForLocaleFrFrAndExampleControl_thenItShouldOnlyContainKey5() {
        Locale.setDefault(Locale.CHINA);

        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundle.resource", new Locale("fr", "FR"), new ExampleControl());

        assertTrue(bundle.keySet()
            .containsAll(Arrays.asList("backButton", "helloLabel")));
    }

    @Test
    public void givenValuesDifferentlyEncoded_whenGetBundleForLocalePlPl_thenItShouldContain3ButtonsAnd1Label() {
        ResourceBundle bundle = ResourceBundle.getBundle("resourcebundle.resource", new Locale("pl", "PL"));

        assertEquals(bundle.getString("helloLabel"), "cześć");
        assertEquals(bundle.getString("helloLabelNoEncoding"), "czeÅ\u009BÄ\u0087");
    }

}
