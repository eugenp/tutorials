package com.baeldung.jvmbitversion;

import com.sun.jna.Platform;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestJVMBitVersion {

    @Test
    public boolean testGetUsingPlatformClass() {
        assertEquals(true, JVMBitVersion.getUsingPlatformClass());
    }

}