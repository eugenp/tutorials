package com.baeldung.jvmbitversion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.baeldung.jvmbitversion.JVMBitVersion;
import com.sun.jna.Platform;

public class JVMBitVersionUnitTest {

    private JVMBitVersion jvmVersion;

    @Before
    public void setup() {
        jvmVersion = new JVMBitVersion();
    }

    @Test
    public void whenUsingSystemClass_thenOutputIsAsExpected() {
        if (System.getProperty("sun.arch.data.model") == "64") {
            assertEquals("64-bit", jvmVersion.getUsingSystemClass());
        } else if (System.getProperty("sun.arch.data.model") == "32") {
            assertEquals("32-bit", jvmVersion.getUsingSystemClass());
        }
    }
    
    @Test
    public void whenUsingNativeClass_thenResultIsAsExpected() {
        if (com.sun.jna.Native.POINTER_SIZE == 8) {
            assertEquals("64-bit", jvmVersion.getUsingNativeClass());
        } else if (com.sun.jna.Native.POINTER_SIZE == 4) {
            assertEquals("32-bit", jvmVersion.getUsingNativeClass());
        }
    }

    @Test
    public void whenUsingPlatformClass_thenResultIsAsExpected() {
        if (Platform.is64Bit() == Boolean.TRUE) {
            assertEquals(Boolean.TRUE, jvmVersion.getUsingPlatformClass());
        } else if (com.sun.jna.Native.POINTER_SIZE == 4) {
            assertEquals(Boolean.FALSE, jvmVersion.getUsingPlatformClass());
        }
    }
}
