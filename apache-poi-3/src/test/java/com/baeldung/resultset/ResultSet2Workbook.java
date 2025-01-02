package com.baeldung.resultset;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class ResultSet2Workbook {

    private final ResultSet resultSet;

    private final ResultSetMetaData resultSetMetaData;

    private int numOfColumns = 0;

    public ResultSet2Workbook(ResultSet rs) throws SQLException {
        this.resultSet = rs;
        this.resultSetMetaData = rs.getMetaData();
        this.numOfColumns = resultSetMetaData.getColumnCount();
    }

    private void createHeaderRow(Sheet sheet) throws SQLException {
        Row row = sheet.createRow(sheet.getLastRowNum()+1);
        for (int n=0; n<numOfColumns; n++) {
            String label = resultSetMetaData.getColumnLabel(n+1);
            Cell cell = row.createCell(n);
            cell.setCellValue(label);
        }
    }

    private void createDataRows(Sheet sheet) throws SQLException {
        while (resultSet.next()) {
            Row row = sheet.createRow(sheet.getLastRowNum()+1);
            for (int n=0; n<numOfColumns; n++) {
                Cell cell = row.createCell(n);
                cell.setCellValue(resultSet.getString(n+1));
            }
        }
    }

    public void write(Workbook workbook) throws IOException, SQLException {
        Sheet sheet = workbook.createSheet("data");
        createHeaderRow(sheet);
        createDataRows(sheet);
    }

}