package com.baeldung;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertTrue;

class CopiesAdditionalResourcesUnitTest {

    @Test
    void givenAdditionalResource_whenCopyingFromSourceToDestination_thenShouldBeInDestination() {
        URL resource = getClass().getClassLoader().getResource("json/include.json");
        File destinationFile = new File(resource.getFile());

        assertTrue(destinationFile.exists());
    }
}
