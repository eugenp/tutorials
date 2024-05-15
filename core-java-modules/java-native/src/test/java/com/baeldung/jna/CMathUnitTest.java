package com.baeldung.jna;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import com.sun.jna.Native;
import com.sun.jna.Platform;

class CMathUnitTest {
    @Test
    void whenCallNative_thenSuccess() {
        CMath lib = Native.load(Platform.isWindows() ? "msvcrt" : "c", CMath.class);
        double result = lib.cosh(0);
        assertEquals(1.0,result);
    }

}
