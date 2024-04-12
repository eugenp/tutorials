package com.baeldung.fastexcel;

import org.dhatim.fastexcel.Workbook;
import org.dhatim.fastexcel.Worksheet;
import org.dhatim.fastexcel.reader.Cell;
import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public class FastexcelHelper {

    public Map<Integer, List<String>> readExcel(String fileLocation) throws IOException {
        Map<Integer, List<String>> data = new HashMap<>();

        try (FileInputStream file = new FileInputStream(fileLocation); ReadableWorkbook wb = new ReadableWorkbook(file)) {
            Sheet sheet = wb.getFirstSheet();
            try (Stream<Row> rows = sheet.openStream()) {
                rows.forEach(r -> {
                    data.put(r.getRowNum(), new ArrayList<>());

                    for (Cell cell : r) {
                        data.get(r.getRowNum()).add(cell.getRawValue());
                    }
                });
            }
        }

        return data;
    }

    public void writeExcel() throws IOException {
        File currDir = new File(".");
        String path = currDir.getAbsolutePath();
        String fileLocation = path.substring(0, path.length() - 1) + "fastexcel.xlsx";

        try (OutputStream os = Files.newOutputStream(Paths.get(fileLocation)); Workbook wb = new Workbook(os, "MyApplication", "1.0")) {
            Worksheet ws = wb.newWorksheet("Sheet 1");

            ws.width(0, 25);
            ws.width(1, 15);

            ws.range(0, 0, 0, 1).style().fontName("Arial").fontSize(16).bold().fillColor("3366FF").set();
            ws.value(0, 0, "Name");
            ws.value(0, 1, "Age");

            ws.range(2, 0, 2, 1).style().wrapText(true).set();
            ws.value(2, 0, "John Smith");
            ws.value(2, 1, 20L);
        }
    }
}
