package com.baeldung.resource;

import java.net.URL;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClassLoaderGetResourceUnitTest {

    @Test
    void givenRelativeResourcePath_whenGetResource_thenReturnNull() {
        URL resourceRelativePath = ClassLoaderGetResourceExample.class.getClassLoader()
            .getResource("example.txt");
        Assertions.assertNull(resourceRelativePath);
    }

    @Test
    void givenAbsoluteResourcePath_whenGetResource_thenReturnResource() {
        URL resourceAbsolutePath = ClassLoaderGetResourceExample.class.getClassLoader()
            .getResource("com/baeldung/resource/example.txt");
        Assertions.assertNotNull(resourceAbsolutePath);
    }
}
