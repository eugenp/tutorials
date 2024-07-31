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
            for (Row row:
                sheet.read()
            ) {
                if(row.getRowNum() == 1) {
                    continue;
                }
                FoodInfo food = new FoodInfo();
                food.setCategory(row.getCellText(0));
                food.setName(row.getCellText(1));
                food.setMeasure(row.getCellText(2));
                food.setCalories(Double.parseDouble(row.getCellText(3)));

                foodData.add(food);

            }
        }

        return foodData;
    }
}
