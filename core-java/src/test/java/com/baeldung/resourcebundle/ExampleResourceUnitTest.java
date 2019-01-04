package com.baeldung.resourcebundle;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * 测试：资源包加载
 * 注意：去加载类路径下的文件
 * 比如：com.baeldung.resourcebundle.ExampleResource，是加载com.baeldung.resourcebundle包下所有ExampleResource开头的并且符合规则的资源。
 */
public class ExampleResourceUnitTest {

    @Test
    public void whenGetBundleExampleResourceForLocalePlPl_thenItShouldInheritPropertiesGreetingAndLanguage() {
        Locale plLocale = new Locale("pl", "PL");

        ResourceBundle exampleBundle = ResourceBundle.getBundle("com.baeldung.resourcebundle.ExampleResource", plLocale);

        Set<String> keySet = exampleBundle.keySet();
        System.out.println("keySet:{}" + keySet);

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
