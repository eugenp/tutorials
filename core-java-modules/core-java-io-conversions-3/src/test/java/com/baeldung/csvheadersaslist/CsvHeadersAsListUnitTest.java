package com.baeldung.csvheadersaslist;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.junit.jupiter.api.Test;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

class CsvHeadersAsListUnitTest {

    private static final String CSV_FILE = "src/test/resources/employees.csv";
    private static final String COMMA_DELIMITER = ",";
    private static final List<String> EXPECTED_HEADERS = List.of("ID", "First name", "Last name", "Salary");

    @Test
    void givenCsvFile_whenUsingBufferedReader_thenGetHeadersAsList() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE))) {
            String csvHeadersLine = reader.readLine();
            List<String> headers = Arrays.asList(csvHeadersLine.split(COMMA_DELIMITER));
            assertThat(headers).containsExactlyElementsOf(EXPECTED_HEADERS);
        }
    }

    @Test
    void givenCsvFile_whenUsingScanner_thenGetHeadersAsList() throws IOException {
        try (Scanner scanner = new Scanner(new File(CSV_FILE))) {
            String csvHeadersLine = scanner.nextLine();
            List<String> headers = Arrays.asList(csvHeadersLine.split(COMMA_DELIMITER));
            assertThat(headers).containsExactlyElementsOf(EXPECTED_HEADERS);
        }
    }

    @Test
    void givenCsvFile_whenUsingOpenCSV_thenGetHeadersAsList() throws CsvValidationException, IOException {
        try (CSVReader csvReader = new CSVReader(new FileReader(CSV_FILE))) {
            List<String> headers = Arrays.asList(csvReader.readNext());
            assertThat(headers).containsExactlyElementsOf(EXPECTED_HEADERS);
        }
    }

    @Test
    void givenCsvFile_whenUsingApacheCommonsCsv_thenGetHeadersAsList() throws IOException {
        CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
            .setDelimiter(COMMA_DELIMITER)
            .setHeader()
            .build();

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE));
             CSVParser parser = CSVParser.parse(reader, csvFormat)) {
            List<String> headers = parser.getHeaderNames();
            assertThat(headers).containsExactlyElementsOf(EXPECTED_HEADERS);
        }
    }

}
