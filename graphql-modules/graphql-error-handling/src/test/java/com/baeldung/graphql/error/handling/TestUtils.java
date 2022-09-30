package com.baeldung.graphql.error.handling;

import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.charset.Charset;

public class TestUtils {
    public static String readFile(String path) throws IOException {
        return IOUtils.toString(
            new ClassPathResource(path).getInputStream(), Charset.defaultCharset()
        );
    }
}
