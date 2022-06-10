package com.baeldung.jvmbitversion;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestJVMBitVersion {

    // Test case 1 : Expected true (Test if platform is 64)
    @Test
    public void testGetUsingPlatformClass() {
        JVMBitVersion jvm = new JVMBitVersion();
        assertEquals(true, jvm.getUsingPlatformClass());
    }

    // Test case 2 Expected a string 64 bit ( Test if system is 64 bit return as string)
    @Test
    public void testGetUsingSystemClass() {
        JVMBitVersion jvm = new JVMBitVersion();
        assertEquals("64-bit", jvm.getUsingSystemClass());
    }
}

