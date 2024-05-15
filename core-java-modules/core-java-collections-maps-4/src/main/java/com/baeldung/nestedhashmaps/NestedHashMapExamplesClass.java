package com.baeldung.nestedhashmaps;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import java.util.List;

public class NestedHashMapExamplesClass {
    public static void main(String[] args) {

        MapsUtil mUtil = new MapsUtil();

        List<String> batterList = new ArrayList<>();
        Map<String, Map<Integer, String>> outerBakedGoodsMap = new HashMap<>();
        Map<String, Map<Integer, String>> outerBakedGoodsMap2 = new HashMap<>();
        Map<String, Map<Integer, String>> outerBakedGoodsMap3 = new HashMap<>();
        Map<String, Map<Integer, String>> outerBakedGoodsMap4 = new HashMap<>();

        batterList.add("Mulberry");
        batterList.add("Cranberry");
        batterList.add("Blackberry");
        batterList.add("Mixed fruit");
        batterList.add("Orange");

        outerBakedGoodsMap.put("Cake", mUtil.buildInnerMap(batterList));

        batterList.clear();
        batterList.add("Candy");
        batterList.add("Dark Chocolate");
        batterList.add("Chocolate");
        batterList.add("Jam filled");
        batterList.add("Pineapple");

        outerBakedGoodsMap.put("Donut", mUtil.buildInnerMap(batterList));

        outerBakedGoodsMap2.put("Eclair", mUtil.buildInnerMap(batterList));
        outerBakedGoodsMap2.put("Donut", mUtil.buildInnerMap(batterList));

        outerBakedGoodsMap3.put("Cake", mUtil.buildInnerMap(batterList));
        batterList.clear();
        batterList.add("Banana");
        batterList.add("Red Velvet");
        batterList.add("Blackberry");
        batterList.add("Passion fruit");
        batterList.add("Kiwi");

        outerBakedGoodsMap3.put("Donut", mUtil.buildInnerMap(batterList));

        outerBakedGoodsMap4.putAll(outerBakedGoodsMap);

        System.out.println(outerBakedGoodsMap.equals(outerBakedGoodsMap2));
        System.out.println(outerBakedGoodsMap.equals(outerBakedGoodsMap3));
        System.out.println(outerBakedGoodsMap.equals(outerBakedGoodsMap4));

        outerBakedGoodsMap.get("Cake")
            .put(6, "Cranberry");
        System.out.println(outerBakedGoodsMap);

        outerBakedGoodsMap.get("Cake")
            .remove(5);
        System.out.println(outerBakedGoodsMap);

        outerBakedGoodsMap.put("Eclair", new HashMap<Integer, String>() {
            {
                put(1, "Dark Chocolate");
            }
        });

        System.out.println(outerBakedGoodsMap);
        outerBakedGoodsMap.remove("Eclair");
        System.out.println(outerBakedGoodsMap);
        System.out.println("Baked Goods Map Flattened: " + mUtil.flattenMap(outerBakedGoodsMap));

        // Employees Map
        List<Employee> listEmployee = new ArrayList<Employee>();

        listEmployee.add(new Employee(1, new Address(124, "Timbuktoo"), "Thorin Oakenshield"));
        listEmployee.add(new Employee(2, new Address(100, "Misty Lanes"), "Balin"));
        listEmployee.add(new Employee(3, new Address(156, "Bramles Lane"), "Bofur"));
        listEmployee.add(new Employee(4, new Address(200, "Bag End"), "Bilbo Baggins"));
        listEmployee.add(new Employee(5, new Address(23, "Rivendell"), "Elrond"));

        Map<Integer, Map<String, String>> employeeAddressMap = mUtil.createNestedMapfromStream(listEmployee);

        Map<Integer, Map<Integer, Address>> employeeMap = mUtil.createNestedObjectMap(listEmployee);
        Map<Integer, Map<Integer, Address>> employeeMap2 = mUtil.createNestedObjectMap(listEmployee);

        listEmployee.clear();
        listEmployee.add(new Employee(1, new Address(500, "Timbuktoo"), "Thorin Oakenshield"));
        listEmployee.add(new Employee(2, new Address(600, "Misty Lanes"), "Balin"));
        listEmployee.add(new Employee(3, new Address(700, "Bramles Lane"), "Bofur"));
        listEmployee.add(new Employee(4, new Address(800, "Bag End"), "Bilbo Baggins"));
        listEmployee.add(new Employee(5, new Address(900, "Rivendell"), "Elrond"));

        Map<Integer, Map<String, String>> employeeAddressMap1 = mUtil.createNestedMapfromStream(listEmployee);

        Map<Integer, Map<Integer, Address>> employeeMap1 = mUtil.createNestedObjectMap(listEmployee);

        System.out.println(employeeMap.equals(employeeMap1));
        System.out.println(employeeMap.equals(employeeMap2));

        for (Map.Entry<String, Map<Integer, String>> outerBakedGoodsMapEntrySet : outerBakedGoodsMap.entrySet()) {
            Map<Integer, String> valueMap = outerBakedGoodsMapEntrySet.getValue();
            System.out.println(valueMap.entrySet());
        }

        for (Map.Entry<Integer, Map<String, String>> employeeEntrySet : employeeAddressMap.entrySet()) {
            Map<String, String> valueMap = employeeEntrySet.getValue();
            System.out.println(valueMap.entrySet());
        }

        System.out.println("Employee Address Map Flattened: " + mUtil.flattenMap(employeeAddressMap));

        System.out.println(employeeAddressMap.equals(employeeAddressMap1));
    }

}
