package com.baeldung.jna;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;

class StdCUnitTest {
    
    @BeforeClass
    public static void setupProtectedMode() {
        Native.setProtected(true);
    }

    @Test
    public void whenMalloc_thenSuccess() {
        StdC lib = StdC.INSTANCE;
        Pointer p = lib.malloc(1024);
        p.setMemory(0l, 1024l, (byte) 0);
        lib.free(p);
    }
    
    @Test
    public void whenAccessViolation_thenShouldThrowError() {
        // Running this test on Linux requires additional setup using libjsig.so
        // Details here: http://java-native-access.github.io/jna/5.6.0/javadoc/overview-summary.html#crash-protection
        // IMPORTANT NOTICE: Code for illustration purposes only. DON'T DO THIS IN YOUR OWN CODE
        if ( Platform.isWindows()) {
            Error e = null;
            Pointer p = new Pointer(0l);
            
            try {
                p.setMemory(0, 100*1024, (byte) 0);
            }
            catch(Error err) {
                e = err;
            }
            
            assertNotNull(e, "Should throw Error");
        }
    }

}
