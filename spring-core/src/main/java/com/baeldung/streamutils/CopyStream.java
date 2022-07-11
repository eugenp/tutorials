package com.baeldung.streamutils;

import org.apache.commons.io.IOUtils;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CopyStream {
    public static String getStringFromInputStream(InputStream input) throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(input, writer, StandardCharsets.UTF_8);

        return writer.toString();
    }

    public InputStream getNonClosingInputStream() throws IOException {
        InputStream in = Files.newInputStream(Paths.get("src/test/resources/input.txt"));

        return StreamUtils.nonClosing(in);
    }
}
