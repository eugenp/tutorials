package com.baeldung.convert.exceldatatolist.poiji;

import java.io.File;
import java.util.List;

import com.baeldung.convert.exceldatatolist.FoodInfo;
import com.poiji.bind.Poiji;

public class ExcelDataToListOfObjectsPOIJI {
    public static List<FoodInfo> excelDataToListOfObjets_withPOIJI(String fileLocation){
        List<FoodInfo> foodData = Poiji.fromExcel(new File(fileLocation), FoodInfo.class);
        return foodData;
    }
}
