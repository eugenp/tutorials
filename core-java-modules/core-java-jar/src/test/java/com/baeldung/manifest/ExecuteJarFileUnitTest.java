package com.baeldung.manifest;

import static org.junit.Assert.*;

import org.junit.Test;

public class ExecuteJarFileUnitTest {

    private static final String ERROR_MESSAGE = "no main manifest attribute, in example.jar\n";
    private static final String SUCCESS_MESSAGE = "AppExample executed!\n";

    @Test
    public final void givenDefaultManifest_whenManifestAttributeIsNotPresent_thenGetErrorMessage() {
        String output = ExecuteJarFile.executeJarWithoutManifestAttribute();
        assertEquals(ERROR_MESSAGE, output);
    }

    @Test
    public final void givenCustomManifest_whenManifestAttributeIsPresent_thenGetSuccessMessage() {
        String output = ExecuteJarFile.executeJarWithManifestAttribute();
        assertEquals(SUCCESS_MESSAGE, output);
    }

}
