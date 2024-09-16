package com.baeldung.streamutils;

import org.apache.commons.io.IOUtils;
import org.springframework.util.StreamUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;

public class CopyStream {
    public static String getStringFromInputStream(InputStream input) throws IOException {
        StringWriter writer = new StringWriter();
        IOUtils.copy(input, writer, "UTF-8");
        return writer.toString();
    }

    public InputStream getNonClosingInputStream() throws IOException {
        InputStream in = new FileInputStream("src/test/resources/input.txt");
        return StreamUtils.nonClosing(in);
    }
}
