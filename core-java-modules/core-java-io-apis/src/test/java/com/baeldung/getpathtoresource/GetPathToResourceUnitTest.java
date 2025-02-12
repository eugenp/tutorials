package com.baeldung.getpathtoresource;

import static org.junit.Assert.assertNotNull;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import org.junit.Test;

public class GetPathToResourceUnitTest {

    @Test
    public void givenFile_whenClassUsed_thenGetResourcePath() {
        URL resourceUrl = GetPathToResourceUnitTest.class.getResource("/sampleText1.txt");
        assertNotNull(resourceUrl);
    }

    @Test
    public void givenFile_whenClassLoaderUsed_thenGetResourcePath() {
        URL resourceUrl = GetPathToResourceUnitTest.class.getClassLoader().getResource("sampleText1.txt");
        assertNotNull(resourceUrl);
    }

    @Test
    public void givenFile_whenPathUsed_thenGetResourcePath() throws Exception {
        Path resourcePath = Paths.get(Objects.requireNonNull(GetPathToResourceUnitTest.class.getResource("/sampleText1.txt")).toURI());
        assertNotNull(resourcePath);
    }

}
