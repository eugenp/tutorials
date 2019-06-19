package com.baeldung.core.resourcenotfound;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ResourceFileReaderUnitTest {
    private ResourceFileReader resourceFileReader;

    @BeforeEach
    void init() {
        resourceFileReader = new ResourceFileReader("test-code.csv");
    }

    @Test
    @DisplayName("Test the code is loaded")
    void test() {
        assertTrue(resourceFileReader.isCodeValid("EFGH"));
    }
}
