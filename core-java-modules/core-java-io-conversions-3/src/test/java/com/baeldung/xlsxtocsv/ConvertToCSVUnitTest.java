package com.baeldung.xlsxtocsv;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConvertToCSVUnitTest {

    private static final String XLSX_FILE_INPUT = "src/test/resources/xlsxToCsv_input.xlsx";
    private static final String CSV_FILE_OUTPUT = "src/test/resources/xlsxToCsv_output.csv";

    @Test
    void givenXlsxFile_whenUsingCommonsCSV_thenGetValuesAsList() throws IOException {
        ConvertToCSV.convertWithCommonsCSV(XLSX_FILE_INPUT, CSV_FILE_OUTPUT);
        List<String> lines = Files.readAllLines(Paths.get(CSV_FILE_OUTPUT));
        assertEquals("1,Dulce,Abril,Female,United States,32,15/10/2017,1562", lines.get(1));
        assertEquals("2,Mara,Hashimoto,Female,Great Britain,25,16/08/2016,1582", lines.get(2));
    }

    @Test
    void givenXlsxFile_whenUsingOpenCSV_thenGetValuesAsList() throws IOException {
        ConvertToCSV.convertWithOpenCSV(XLSX_FILE_INPUT, CSV_FILE_OUTPUT);
        List<String> lines = Files.readAllLines(Paths.get(CSV_FILE_OUTPUT));
        assertEquals("1,Dulce,Abril,Female,United States,32,15/10/2017,1562", lines.get(1));
        assertEquals("2,Mara,Hashimoto,Female,Great Britain,25,16/08/2016,1582", lines.get(2));
    }
}