package com.baeldung.junit5.registerextension;

import com.baeldung.junit5.registerextension.RegisterExtensionSampleExtension;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

/**
 * This test demonstrates the use of the same extension in two ways.
 * 1. Once as instance level field: Only method level callbacks are called.
 * 2. Once as class level static field: All methods are called.
 */
public class RegisterExtensionUnitTest {

    @RegisterExtension
    static RegisterExtensionSampleExtension staticExtension = new RegisterExtensionSampleExtension("static version");

    @RegisterExtension
    RegisterExtensionSampleExtension instanceLevelExtension = new RegisterExtensionSampleExtension("instance version");

    @Test
    public void demoTest() {
        Assertions.assertEquals("instance version", instanceLevelExtension.getType());
    }

}
