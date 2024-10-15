package com.baeldung.poi.benchmark;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFFormulaEvaluator;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SXSSFWorkbookFunctionTest {

    private Workbook workbook;
    private Sheet sheet;

    @BeforeEach
    void setup() {
        workbook = new SXSSFWorkbook(1);
        sheet = workbook.createSheet("Test Sheet");
        sheet.createRow(0).createCell(0).setCellValue(5);
        sheet.createRow(1).createCell(0).setCellValue(15);
    }

    @Test
    void whenAutoSizeColumnOnSXSSFWorkbook_thenThrowsIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> sheet.autoSizeColumn(0));
    }

    @Test
    void whenCloneSheetOnSXSSFWorkbook_thenThrowsIllegalStateException() {
        assertThrows(IllegalStateException.class, () -> workbook.cloneSheet(0));
    }

    @Test
    void whenEvaluateFormulaCellOnSXSSFWorkbook_thenThrowsIllegalStateException() {
        Cell formulaCell = sheet.createRow(sheet.getLastRowNum()).createCell(0);
        formulaCell.setCellFormula("SUM(A1:B1)");

        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        assertThrows(SXSSFFormulaEvaluator.RowFlushedException.class, () -> evaluator.evaluateFormulaCell(formulaCell));
    }

    @Test
    void whenGetRowOnSXSSFWorkbook_thenReturnNull() {
        Row row = sheet.getRow(0);
        assertThat(row).isNull();
    }

    @Test
    void whenShiftColumnsOnSXSSFWorkbook_thenThrowsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> sheet.shiftColumns(0, 2, 1));
    }

    @AfterEach
    void cleanUp() throws IOException {
        workbook.close();
    }

}