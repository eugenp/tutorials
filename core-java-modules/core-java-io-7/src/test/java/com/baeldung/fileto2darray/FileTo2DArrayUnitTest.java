package com.baeldung.fileto2darray;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class FileTo2DArrayUnitTest {
    String[][] expectedData = {
            {"value1", "value2", "value3"},
            {"value4", "value5", "value6"},
            {"value7", "value8", "value9"}
    };

    public static String[][] readFileTo2DArrayUsingBufferedReader(String filePath) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            List<String[]> dataList = new ArrayList<>();
            String line;
            while ((line = br.readLine()) != null) {
                dataList.add(line.split(","));
            }
            return dataList.toArray(new String[0][]);
        }
    }

    public static String[][] readFileTo2DArrayUsingStreamAPI(String filePath) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filePath));
        return lines.stream()
                .map(line -> line.split(","))
                .toArray(String[][]::new);
    }

    public static String[][] readFileTo2DArrayUsingApacheCommonsCSV(String filePath) throws IOException {
        Reader reader = new FileReader(filePath);
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        List<String[]> dataList = new ArrayList<>();
        for (CSVRecord record : csvParser) {
            String[] data = new String[record.size()];
            for (int i = 0; i < record.size(); i++) {
                data[i] = record.get(i);
            }
            dataList.add(data);
        }

        return dataList.toArray(new String[dataList.size()][]);
    }

    @Test
    public void givenFile_whenUsingBufferedReader_thenArrayIsCorrect() throws IOException {
        String[][] actualData = readFileTo2DArrayUsingBufferedReader("src/test/resources/test_file.txt");

        assertArrayEquals(expectedData, actualData);
    }

    @Test
    public void givenFile_whenUsingStreamAPI_thenArrayIsCorrect() throws IOException {
        String[][] actualData = readFileTo2DArrayUsingStreamAPI("src/test/resources/test_file.txt");

        assertArrayEquals(expectedData, actualData);
    }

    @Test
    public void givenFile_whenUsingApacheCommonsCSV_thenArrayIsCorrect() throws IOException {
        String[][] actualData = readFileTo2DArrayUsingApacheCommonsCSV("src/test/resources/test_file.csv");

        assertArrayEquals(expectedData, actualData);
    }
}