package com.baeldung.jna;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.junit.jupiter.api.Test;

import com.baeldung.jna.NativeFS.Stat;
import com.sun.jna.LastErrorException;
import com.sun.jna.Platform;

public class NativeFSUnitTest {
    

    @Test
    public void whenCallNative_thenSuccess() throws IOException {
        NativeFS lib = NativeFS.INSTANCE;
        
        File f = Files.createTempFile("junit", ".bin").toFile(); 
        f.deleteOnExit();
        Stat stat = new Stat();
        try {
            if (Platform.isWindows()) {
                int rc = lib.stat(f.getAbsolutePath(), stat);
                assertEquals(0, rc);
                assertEquals(0,stat.st_size.longValue());
            }
        }
        catch(LastErrorException error) {
            fail("stat failed: error code=" + error.getErrorCode());
        }
        
    }
}
