package com.baeldung.convert.exceldatatolist.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.baeldung.convert.exceldatatolist.FoodInfo;

public class ExcelDataToListApachePOI {
    public static List<FoodInfo> excelDataToListOfObjets_withApachePOI(String fileLocation) throws IOException {
        FileInputStream file = new FileInputStream(new File(fileLocation));
        Workbook workbook = new XSSFWorkbook(file);
        Sheet sheet = workbook.getSheetAt(0);
        List<FoodInfo> foodData = new ArrayList<FoodInfo>();
        DataFormatter dataFormatter = new DataFormatter();
        for (int n = 1; n < sheet.getPhysicalNumberOfRows(); n++) {
            Row row = sheet.getRow(n);
            FoodInfo foodInfo = new FoodInfo();
            int i = row.getFirstCellNum();

            foodInfo.setCategory(dataFormatter.formatCellValue(row.getCell(i)));
            foodInfo.setName(dataFormatter.formatCellValue(row.getCell(++i)));
            foodInfo.setMeasure(dataFormatter.formatCellValue(row.getCell(++i)));
            foodInfo.setCalories(Double.parseDouble(dataFormatter.formatCellValue(row.getCell(++i))));

            foodData.add(foodInfo);

        }
        return foodData;
    }
}
