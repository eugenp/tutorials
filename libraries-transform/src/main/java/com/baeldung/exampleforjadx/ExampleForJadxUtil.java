package com.baeldung.exampleforjadx;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class ExampleForJadxUtil {

    public String resourceFileReader(String fileName) throws IOException, FileNotFoundException {
        try (InputStream in = getClass().getResourceAsStream(fileName); BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String result = null;
            if (in != null) {
                result = reader.lines()
                    .collect(Collectors.joining("\n"));
            }
            return result;
        }
    }

    public String capitalizeString(String str) {
        IntStream chars = str.chars();
        String intStreamToString = chars.map(p -> Character.toUpperCase((char) p))
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
        return intStreamToString;
    }
}
