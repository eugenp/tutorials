package com.baeldung.manifest;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExecuteJarFileTest {

    private static final String ERROR_MESSAGE = "no main manifest attribute, in example.jar\n";
    private static final String SUCCESS_MESSAGE = "AppExample executed!\n";

    @Test
    public final void testExecuteJarWithoutManifest() {
        String output = ExecuteJarFile.executeJarWithoutManifestAttribute();
        assertEquals(ERROR_MESSAGE, output);
    }

    @Test
    public final void testExecuteJarWithManifest() {
        String output = ExecuteJarFile.executeJarWithManifestAttribute();
        assertEquals(SUCCESS_MESSAGE, output);
    }

}
