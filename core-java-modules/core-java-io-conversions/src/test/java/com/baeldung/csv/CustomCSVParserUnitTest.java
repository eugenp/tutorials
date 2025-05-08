package com.baeldung.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

 

public class CustomCSVParserUnitTest {
    public static final String COMMA_DELIMITER = ",";
   
    public static final String CSV_FILE  = "src/test/resources/book3.csv";

    public static final List<List<String>> EXPECTED_ARRAY = Collections.unmodifiableList(new ArrayList<List<String>>() {
        {
            add(new ArrayList<String>() {
                {
                    add("Kom, Mary");
                    add("Unbreakable");
                }
            });
            add(new ArrayList<String>() {
                {
                    add("Isapuari, Kapil");
                    add("Farishta");
                }
            });
        }
    });
    
    @Test
    public void givenCSVFileWithCommaInValues_whenCustomCSVParser_thenContentsAsExpected() throws IOException {
        List<List<String>> records = new ArrayList<List<String>>();
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_FILE))) {
            String line = "";
            while ((line = br.readLine()) != null) {
                records.add(parseLine(line));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < EXPECTED_ARRAY.size(); i++) {
            Assert.assertArrayEquals(EXPECTED_ARRAY.get(i)
                .toArray(),
                records.get(i)
                    .toArray());
        }
    }

    private static List<String> parseLine(String line) {
        List<String> values = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentValue = new StringBuilder();

        for (char c : line.toCharArray()) {
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                values.add(currentValue.toString());
                currentValue = new StringBuilder();
            } else {
                currentValue.append(c);
            }
        }
        values.add(currentValue.toString());
        return values;
    }
}
