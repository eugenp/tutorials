package com.baeldung.jvmbitversion;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.sun.jna.Platform;

public class JVMBitVersionUnitTest {

    private JVMBitVersion jvmVersion;

    @Before
    public void setup() {
        jvmVersion = new JVMBitVersion();
    }

    @Test
    public void whenUsingSystemClass_thenOutputIsAsExpected() {
        if ("64".equals(System.getProperty("sun.arch.data.model"))) {
            assertEquals("64-bit", jvmVersion.getUsingSystemClass());
        } else if ("32".equals(System.getProperty("sun.arch.data.model"))) {
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
