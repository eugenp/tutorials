package com.baeldung.resource;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ResourceUtils {
    public static String readFileToString(String path) throws IOException {
        return FileUtils.readFileToString(org.springframework.util.ResourceUtils.getFile(path), StandardCharsets.UTF_8);
    }
}
