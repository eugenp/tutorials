package com.baeldung.commons.compress;

import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public interface TestResources {

    String DIR = "/compress/";

    static InputStream compressedArchive() {
        return TestResources.class.getResourceAsStream(DIR + CompressUtilsUnitTest.COMPRESSED_ARCHIVE);
    }

    static Path testFile() throws URISyntaxException {
        URL resource = TestResources.class.getResource(DIR + "new.txt");
        if (resource == null) {
            throw new IllegalArgumentException("file not found!");
        } else {
            return Paths.get(resource.toURI());
        }
    }
}