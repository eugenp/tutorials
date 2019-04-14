package com.baeldung.resource;

import org.apache.commons.io.FileUtils;

import java.io.IOException;
import java.nio.charset.Charset;

public class ResourceUtils {
    public static String readFileToString(String path) throws IOException {
        return FileUtils.readFileToString(FileUtils.getFile(path), Charset.defaultCharset());
    }
}
