package com.baeldung.readinputcharbychar;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Scanner;

import static org.junit.Assert.assertArrayEquals;

public class ReadInputCharByCharUnitTest {

    @Test
    public void givenInputFromConsole_whenUsingBufferedStream_thenReadCharByChar() throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("TestInput".getBytes());
        System.setIn(inputStream);

        try (BufferedReader buffer = new BufferedReader(new InputStreamReader(System.in))) {
            char[] result = new char[9];
            int index = 0;
            int c;
            while ((c = buffer.read()) != -1) {
                result[index++] = (char) c;
            }

            assertArrayEquals("TestInput".toCharArray(), result);
        }
    }

    @Test
    public void givenInputFromFile_whenUsingFileReader_thenReadCharByChar() throws IOException {
        File tempFile = File.createTempFile("tempTestFile", ".txt");
        FileWriter fileWriter = new FileWriter(tempFile);
        fileWriter.write("TestFileContent");
        fileWriter.close();

        try (FileReader fileReader = new FileReader(tempFile.getAbsolutePath())) {
            char[] result = new char[15]; // Adjust the size based on your input
            int index = 0;
            int charCode;
            while ((charCode = fileReader.read()) != -1) {
                result[index++] = (char) charCode;
            }

            assertArrayEquals("TestFileContent".toCharArray(), result);
        }
    }

    @Test
    public void givenInputFromConsole_whenUsingScanner_thenReadCharByChar() {
        ByteArrayInputStream inputStream = new ByteArrayInputStream("TestInput".getBytes());
        System.setIn(inputStream);

        try (Scanner scanner = new Scanner(System.in)) {
            if (scanner.hasNext()) {
                char[] result = scanner.next().toCharArray();
                assertArrayEquals("TestInput".toCharArray(), result);
            }
        }
    }
}
