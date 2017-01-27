package com.baeldung.excel;

import jxl.*;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import jxl.read.biff.BiffException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.stereotype.Service;
import jxl.write.*;
import java.util.Date;

@Service
public class JExcelHelper {

    public Map<Integer, ArrayList<String>> readJExcel(String fileLocation) throws IOException, BiffException {
        Map<Integer, ArrayList<String>> data = new HashMap<Integer, ArrayList<String>>();

        Workbook workbook = Workbook.getWorkbook(new File(fileLocation));
        Sheet sheet = workbook.getSheet(0);
        int rows = sheet.getRows();
        int columns = sheet.getColumns();

        for (int i = 0; i < rows; i++) {
            data.put(new Integer(i), new ArrayList<String>());
            for (int j = 0; j < columns; j++) {
                data.get(new Integer(i))
                    .add(sheet.getCell(j, i)
                        .getContents());
            }
        }
        return data;
    }

    public void writeJExcel() throws IOException, WriteException {
        WritableWorkbook workbook = null;
        try {
            File currDir = new File(".");
            String path = currDir.getAbsolutePath();
            String fileLocation = path.substring(0, path.length() - 1) + "temp.xls";

            workbook = Workbook.createWorkbook(new File(fileLocation));

            WritableSheet sheet = workbook.createSheet("Sheet 1", 0);

            WritableCellFormat cellFormat = new WritableCellFormat();
            WritableFont font = new WritableFont(WritableFont.ARIAL, 16, WritableFont.BOLD);
            cellFormat.setFont(font);
            cellFormat.setBackground(Colour.YELLOW);
            cellFormat.setWrap(true);
            Label dateLabel = new Label(0, 0, "Today is", cellFormat);
            sheet.setColumnView(0, 20);
            sheet.addCell(dateLabel);

            WritableCellFormat dateFormat = new WritableCellFormat(DateFormats.FORMAT1);
            dateFormat.setWrap(true);
            DateTime dateTime = new DateTime(1, 0, new Date(), dateFormat);
            sheet.setColumnView(0, 20);
            sheet.addCell(dateTime);

            workbook.write();
        } finally {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (WriteException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}