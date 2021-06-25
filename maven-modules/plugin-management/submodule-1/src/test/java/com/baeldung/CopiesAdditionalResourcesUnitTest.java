package com.baeldung;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URL;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CopiesAdditionalResourcesUnitTest {

    @Test
    public void givenAdditionalResource_whenCopyingFromSourceToDestination_thenShouldBeInDestination() {
        URL resource = getClass().getClassLoader().getResource("json/include.json");
        File destinationFile = new File(Objects.requireNonNull(resource).getFile());

        assertTrue(destinationFile.exists());
    }
}
