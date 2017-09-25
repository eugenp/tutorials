package com.baeldung.streamex;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

import one.util.streamex.StreamEx;

public class FileUtil {

    public void read(Reader reader) throws IOException {
        StreamEx.ofLines(reader).remove(String::isEmpty).forEach(System.out::println);
    }

    public void write(Reader reader, Writer writer) throws IOException {
        StreamEx.ofLines(reader).remove(String::isEmpty).forEach(line -> {
            try {
                writer.write(line);
                writer.write(System.lineSeparator());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
