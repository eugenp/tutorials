package com.baeldung.poi.excel.insert;

import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

public class ExcelInsertRowUnitTest {
    private static final String FILE_NAME = "test.xlsx";
    private static final String NEW_FILE_NAME = "new_test.xlsx";
    private String fileLocation;
    private InsertRowHelper insertRowHelper;

    @Before
    public void setup() throws URISyntaxException {
        fileLocation = Paths.get(ClassLoader.getSystemResource(FILE_NAME).toURI()).toString();
        insertRowHelper = new InsertRowHelper();
    }

    @Test
    public void givenWorkbook_whenInsertRowBetween_thenRowCreated() throws IOException {
        Workbook newWorkbook = insertRowHelper.insertRowBetweenRows(fileLocation, 2, 1);
        FileOutputStream outputStream = new FileOutputStream(NEW_FILE_NAME);
        newWorkbook.write(outputStream);
        File file = new File(NEW_FILE_NAME);

        final int expectedRowResult = 5;
        Assertions.assertEquals(expectedRowResult, newWorkbook.getSheetAt(0).getLastRowNum());

        outputStream.close();
        file.delete();
        newWorkbook.close();
    }

}