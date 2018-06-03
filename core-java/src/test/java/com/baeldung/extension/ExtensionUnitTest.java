package com.baeldung.extension;

import org.junit.Assert;
import org.junit.Test;

public class ExtensionUnitTest {
    private Extension extension = new Extension();

    @Test
    public void getExtension_whenApacheCommonIO_thenExtensionIsTrue() {
        String expectedExtension = "txt";
        String actualExtension = extension.getExtensionByApacheCommonLib("jarvis.txt");
        Assert.assertEquals(expectedExtension, actualExtension);
    }

    @Test
    public void getExtension_whenStringHandle_thenExtensionIsTrue() {
        String expectedExtension = "java";
        String actualExtension = extension.getExtensionByStringHandling("Demo.java");
        Assert.assertEquals(expectedExtension, actualExtension);
    }

    @Test
    public void getExtension_whenGuava_thenExtensionIsTrue() {
        String expectedExtension = "class";
        String actualExtension = extension.getExtensionByGuava("baeldung/Demo.class");
        Assert.assertEquals(expectedExtension, actualExtension);
    }
}
