package com.baeldung.stream;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class OutputStreamExamples {

    public void fileOutputStreamByteSequence(String file, String data) throws IOException {
        byte[] bytes = data.getBytes();
        try (OutputStream out = new FileOutputStream(file)) {
            out.write(bytes);
        }
    }

    public void fileOutputStreamByteSubSequence(String file, String data) throws IOException {
        byte[] bytes = data.getBytes();
        try (OutputStream out = new FileOutputStream(file)) {
            out.write(bytes, 6, 5);
        }
    }

    public void fileOutputStreamByteSingle(String file, String data) throws IOException {
        byte[] bytes = data.getBytes();
        try (OutputStream out = new FileOutputStream(file)) {
            out.write(bytes[6]);
        }
    }

    public void bufferedOutputStream(String file, String... data) throws IOException {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(file))) {
            for (String s : data) {
                out.write(s.getBytes());
                out.write(" ".getBytes());
            }
        }
    }

    public void outputStreamWriter(String file, String data) throws IOException {
        try (OutputStream out = new FileOutputStream(file); Writer writer = new OutputStreamWriter(out, "UTF-8")) {
            writer.write(data);
        }
    }

}
