package com.baeldung.hssfworkbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class ExcelConverter {

    public static byte[] convertWorkbookToBytes(HSSFWorkbook workbook) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            workbook.write(baos);
            return baos.toByteArray();
        }
    }

    public static HSSFWorkbook convertBytesToWorkbook(byte[] excelBytes) throws IOException {
        try (ByteArrayInputStream bais = new ByteArrayInputStream(excelBytes)) {
            return new HSSFWorkbook(bais);
        }
    }
}
