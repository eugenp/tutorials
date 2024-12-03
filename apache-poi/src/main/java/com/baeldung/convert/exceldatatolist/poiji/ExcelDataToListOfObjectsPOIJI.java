package com.baeldung.convert.exceldatatolist.poiji;

import com.baeldung.convert.exceldatatolist.FoodInfo;
import com.poiji.bind.Poiji;

import java.io.File;
import java.util.List;

public class ExcelDataToListOfObjectsPOIJI {
    public static List<FoodInfo> excelDataToListOfObjets_withPOIJI(String fileLocation){
        return Poiji.fromExcel(new File(fileLocation), FoodInfo.class);
    }
}
