package com.baeldung.exceltojson;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;

public class ExcelToJsonUnitTest {
    public static String filePath = Objects.requireNonNull(ExcelToJsonUnitTest.class.getClassLoader().getResource("Book1.xlsx")).getFile();
    public String expectedJson = "[[\"C1\",\"C2\",\"C3\",\"C4\",\"C5\"]," +
            "[\"1.0\",\"2.0\",\"3.0\",\"4.0\",\"5.0\"]," +
            "[\"1.0\",\"2.0\",\"3.0\",\"4.0\",\"5.0\"]," +
            "[\"1.0\",\"2.0\",\"3.0\",\"4.0\",\"5.0\"]," +
            "[\"1.0\",\"2.0\",\"3.0\",\"4.0\",\"5.0\"]]";
    private Workbook workbook;
    private Sheet sheet;
    private InputStream inputStream;

    public ExcelToJsonUnitTest() throws IOException {
        inputStream = new FileInputStream(filePath);
        workbook = new XSSFWorkbook(inputStream);
        sheet = workbook.getSheetAt(0);
    }

    @Test
    public void givenExcelFile_whenUsingApachePOIConversion_thenConvertToJson() {
        JSONArray jsonArray = new JSONArray();

        Row headerRow = sheet.getRow(0);
        List<String> headers = new ArrayList<>();
        for (Cell cell : headerRow) {
            headers.add(cell.toString());
        }
        jsonArray.put(headers);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            List<String> rowData = new ArrayList<>();
            for (Cell cell : row) {
                rowData.add(cell.toString());
            }
            jsonArray.put(rowData);
        }

        assertEquals(expectedJson, jsonArray.toString());
    }

    @Test
    public void givenExcelFile_whenUsingJacksonConversion_thenConvertToJson() throws JsonProcessingException {
        List<List<String>> data = new ArrayList<>();

        Row headerRow = sheet.getRow(0);
        List<String> headers = new ArrayList<>();
        for (Cell cell : headerRow) {
            headers.add(cell.toString());
        }
        data.add(headers);

        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            List<String> rowData = new ArrayList<>();
            for (Cell cell : row) {
                rowData.add(cell.toString());
            }
            data.add(rowData);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(data);

        assertEquals(expectedJson, json);
    }
}
