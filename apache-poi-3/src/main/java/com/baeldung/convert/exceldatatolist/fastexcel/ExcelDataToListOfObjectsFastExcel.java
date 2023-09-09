package com.baeldung.convert.exceldatatolist.fastexcel;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.dhatim.fastexcel.reader.ReadableWorkbook;
import org.dhatim.fastexcel.reader.Row;
import org.dhatim.fastexcel.reader.Sheet;

import com.baeldung.convert.exceldatatolist.FoodInfo;

public class ExcelDataToListOfObjectsFastExcel {
    public static List<FoodInfo> excelDataToListOfObjets_withFastExcel(String fileLocation)throws IOException, NumberFormatException {
        List<FoodInfo> foodData = new ArrayList<FoodInfo>();

        try (FileInputStream file = new FileInputStream(fileLocation);
            ReadableWorkbook wb = new ReadableWorkbook(file)) {
            Sheet sheet = wb.getFirstSheet();
            for (Row r:
                sheet.read()
            ) {
                if(r.getRowNum() == 1) {
                    continue;
                }
                FoodInfo food = new FoodInfo();
                food.setCategory(r.getCellText(0));
                food.setName(r.getCellText(1));
                food.setMeasure(r.getCellAsString(2).toString());
                food.setCalories(Double.parseDouble(r.getCellText(3)));
                food.setProtein(Double.parseDouble(r.getCellText(4)));
                food.setFat(Double.parseDouble(r.getCellText(5)));
                food.setCarbs(Double.parseDouble(r.getCellText(6)));
                food.setFiber(Double.parseDouble(r.getCellText(7)));

                foodData.add(food);

            }
        }

        return foodData;
    }
}
