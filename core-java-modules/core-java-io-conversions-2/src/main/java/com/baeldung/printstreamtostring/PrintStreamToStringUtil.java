package com.baeldung.printstreamtostring;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;

public class PrintStreamToStringUtil {

    public static String usingByteArrayOutputStreamClass(String input) throws IOException {
        if (input == null) {
            return null;
        }

        String output;
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream(); PrintStream printStream = new PrintStream(outputStream)) {
            printStream.print(input);

            output = outputStream.toString();
        }

        return output;
    }

    public static String usingApacheCommonsIO(String input) {
        if (input == null) {
            return null;
        }

        org.apache.commons.io.output.ByteArrayOutputStream outputStream = new org.apache.commons.io.output.ByteArrayOutputStream();
        try (PrintStream printStream = new PrintStream(outputStream)) {
            printStream.print(input);
        }

        return new String(outputStream.toByteArray());
    }

    public static String usingCustomOutputStream(String input) throws IOException {
        if (input == null) {
            return null;
        }

        String output;
        try (CustomOutputStream outputStream = new CustomOutputStream(); PrintStream printStream = new PrintStream(outputStream)) {
            printStream.print(input);

            output = outputStream.toString();
        }

        return output;
    }

    private static class CustomOutputStream extends OutputStream {

        private StringBuilder stringBuilder = new StringBuilder();

        @Override
        public void write(int b) throws IOException {
            this.stringBuilder.append((char) b);
        }

        @Override
        public String toString() {
            return this.stringBuilder.toString();
        }
    }

}
