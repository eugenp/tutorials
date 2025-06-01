package com.baeldung.resource;

import java.net.URL;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class ClassGetResourceUnitTest {

    @Test
    void givenRelativeResourcePath_whenGetResource_thenReturnResource() {
        URL resourceRelativePath = ClassGetResourceExample.class.getResource("example.txt");
        Assertions.assertNotNull(resourceRelativePath);
    }

    @Test
    void givenAbsoluteResourcePath_whenGetResource_thenReturnResource() {
        URL resourceAbsolutePath = ClassGetResourceExample.class.getResource("/com/baeldung/resource/example.txt");
        Assertions.assertNotNull(resourceAbsolutePath);
    }
}
