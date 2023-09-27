package com.baeldung.list.multipleobjecttypes;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MultipleObjectTypeArrayList {

    public static void main(String[] args) {

        ArrayList<Object> multiTypeList = new ArrayList<>();

        multiTypeList.add(Integer.valueOf(10));
        multiTypeList.add(Double.valueOf(11.5));
        multiTypeList.add("String Data");
        multiTypeList.add(Arrays.asList(1, 2, 3));
        multiTypeList.add(new CustomObject("Class Data"));
        multiTypeList.add(BigInteger.valueOf(123456789));
        multiTypeList.add(LocalDate.of(2023, 9, 19));

        for (Object dataObj : multiTypeList) {
            if (dataObj instanceof Integer intData)
                System.out.println("Integer Data : " + intData);
            else if (dataObj instanceof Double doubleData)
                System.out.println("Double Data : " + doubleData);
            else if (dataObj instanceof String stringData)
                System.out.println("String Data : " + stringData);
            else if (dataObj instanceof List<?> intList)
                System.out.println("List Data : " + intList);
            else if (dataObj instanceof CustomObject customObj)
                System.out.println("CustomObject Data : " + customObj.getClassData());
            else if (dataObj instanceof BigInteger bigIntData)
                System.out.println("BigInteger Data : " + bigIntData);
            else if (dataObj instanceof LocalDate localDate)
                System.out.println("LocalDate Data : " + localDate.toString());
        }
    }
}
