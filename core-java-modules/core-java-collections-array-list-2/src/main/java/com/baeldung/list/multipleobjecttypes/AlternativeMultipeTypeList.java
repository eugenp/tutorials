package com.baeldung.list.multipleobjecttypes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

public class AlternativeMultipeTypeList {

    public static void main(String[] args) {
        // List of Parent Class
        ArrayList<Number> myList = new ArrayList<>();
        myList.add(1.2);
        myList.add(2);
        myList.add(-3.5);

        // List of Interface type
        ArrayList<Map> diffMapList = new ArrayList<>();
        diffMapList.add(new HashMap<>());
        diffMapList.add(new TreeMap<>());
        diffMapList.add(new LinkedHashMap<>());

        // List of Custom Object
        ArrayList<CustomObject> objList = new ArrayList<>();
        objList.add(new CustomObject("String"));
        objList.add(new CustomObject(2));

        // List via Functional Interface
        List<Object> dataList = new ArrayList<>();

        Predicate<Object> myPredicate = inputData -> (inputData instanceof String || inputData instanceof Integer);

        UserFunctionalInterface myInterface = (listObj, data) -> {
            if (myPredicate.test(data))
                listObj.add(data);
            else
                System.out.println("Skipping input as data not allowed for class: " + data.getClass()
                    .getSimpleName());
            return listObj;
        };

        myInterface.addToList(dataList, Integer.valueOf(2));
        myInterface.addToList(dataList, Double.valueOf(3.33));
        myInterface.addToList(dataList, "String Value");
        myInterface.printList(dataList);
    }

}
