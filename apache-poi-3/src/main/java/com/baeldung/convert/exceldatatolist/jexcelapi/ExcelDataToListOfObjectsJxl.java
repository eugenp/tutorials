package com.baeldung.convert.exceldatatolist.jexcelapi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.baeldung.convert.exceldatatolist.FoodInfo;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class ExcelDataToListOfObjectsJxl {
    public static List<FoodInfo> excelDataToListOfObjets_withJxl(String fileLocation) throws IOException, BiffException {

        List<FoodInfo> foodData = new ArrayList<FoodInfo>();

        Workbook workbook = Workbook.getWorkbook(new File(fileLocation));
        Sheet sheet = workbook.getSheet(0);

        int rows = sheet.getRows();

        for (int i = 1; i < rows; i++) {
            FoodInfo foodInfo = new FoodInfo();

            foodInfo.setCategory(sheet.getCell(0, i).getContents());
            foodInfo.setName(sheet.getCell(1, i).getContents());
            foodInfo.setMeasure(sheet.getCell(2, i).getContents());
            foodInfo.setCalories(Double.parseDouble(sheet.getCell(3, i).getContents()));
            foodInfo.setProtein(Double.parseDouble(sheet.getCell(4, i).getContents()));
            foodInfo.setFat(Double.parseDouble(sheet.getCell(5, i).getContents()));
            foodInfo.setCarbs(Double.parseDouble(sheet.getCell(6, i).getContents()));
            foodInfo.setFiber(Double.parseDouble(sheet.getCell(7, i).getContents()));

            foodData.add(foodInfo);

        }
        return foodData;
    }
}
