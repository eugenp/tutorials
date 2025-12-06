package com.baeldung.hssfworkbook;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

import com.baeldung.hssfworkbook.ExcelConverter;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ExcelConverterTest {

    private HSSFWorkbook createMinimalWorkbook() {
        HSSFWorkbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("TestSheet");
        sheet.createRow(0)
          .createCell(0)
          .setCellValue("Test Data");
        return workbook;
    }

    private byte[] convertWorkbookToBytesSafely(HSSFWorkbook workbook) {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            workbook.write(baos);
            return baos.toByteArray();
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to create test bytes.", e);
        }
    }

    @Test
    @DisplayName("GivenAValidHSSFWorkbook_whenConvertingToBytes_thenByteArrayIsNonEmptyAndValid")
    void givenAValidHSSFWorkbook_whenConvertingToBytes_thenByteArrayIsNonEmptyAndValid() throws IOException {
        HSSFWorkbook workbook = createMinimalWorkbook();

        byte[] resultBytes = ExcelConverter.convertWorkbookToBytes(workbook);

        assertNotNull(resultBytes, "The byte array should not be null.");
        assertTrue(resultBytes.length > 0, "The byte array should not be empty.");

        HSSFWorkbook convertedWorkbook = ExcelConverter.convertBytesToWorkbook(resultBytes);
        assertEquals("TestSheet", convertedWorkbook.getSheetName(0));
    }

    @Test
    @DisplayName("GivenAValidExcelByteArray_whenConvertingToWorkbook_thenHSSFWorkbookIsReturned")
    void givenAValidExcelByteArray_whenConvertingToWorkbook_thenHSSFWorkbookIsReturned() throws IOException {
        HSSFWorkbook originalWorkbook = createMinimalWorkbook();
        byte[] validExcelBytes = convertWorkbookToBytesSafely(originalWorkbook);

        HSSFWorkbook resultWorkbook = ExcelConverter.convertBytesToWorkbook(validExcelBytes);

        assertNotNull(resultWorkbook, "The resulting workbook should not be null.");
        assertEquals(1, resultWorkbook.getNumberOfSheets(), "The resulting workbook should have 1 sheet.");
        assertEquals("TestSheet", resultWorkbook.getSheetName(0), "Sheet name should match the original.");
        assertEquals("Test Data", resultWorkbook.getSheetAt(0)
          .getRow(0)
          .getCell(0)
          .getStringCellValue());
    }

    @Test
    @DisplayName("GivenAnEmptyByteArray_whenConvertingToWorkbook_thenIOExceptionIsThrown")
    void givenAnEmptyByteArray_whenConvertingToWorkbook_thenIOExceptionIsThrown() {
        byte[] emptyBytes = new byte[0];

        assertThrows(IOException.class, () -> ExcelConverter.convertBytesToWorkbook(emptyBytes), "Expected IOException for empty byte array.");
    }

    @Test
    @DisplayName("GivenAnInvalidByteArray_whenConvertingToWorkbook_thenIOExceptionIsThrown")
    void givenAnInvalidByteArray_whenConvertingToWorkbook_thenIOExceptionIsThrown() {
        byte[] invalidBytes = { 0x01, 0x02, 0x03, 0x04, 0x05 };

        assertThrows(IOException.class, () -> ExcelConverter.convertBytesToWorkbook(invalidBytes), "Expected IOException for invalid byte array format.");
    }
}