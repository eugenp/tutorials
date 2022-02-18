package com.baeldung.map.hashmap.entryremoval;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class RemoveEntryApplication {

    public static void main(String[] args) {

        HashMap<String, String> foodItemTypeMap = new HashMap<>();
        foodItemTypeMap.put("Apple", "Fruit");
        foodItemTypeMap.put("Grape", "Fruit");
        foodItemTypeMap.put("Mango", "Fruit");
        foodItemTypeMap.put("Carrot", "Vegetable");
        foodItemTypeMap.put("Potato", "Vegetable");
        foodItemTypeMap.put("Spinach", "Vegetable");
        // Current Map Status: {Potato=Vegetable, Apple=Fruit, Carrot=Vegetable, Grape=Fruit, Mango=Fruit, Spinach=Vegetable}

        foodItemTypeMap.remove("Apple");
        // Current Map Status: {Potato=Vegetable, Carrot=Vegetable, Grape=Fruit, Mango=Fruit, Spinach=Vegetable}

        foodItemTypeMap.remove("Grape", "Vegetable");
        // Current Map Status: {Potato=Vegetable, Carrot=Vegetable, Grape=Fruit, Mango=Fruit, Spinach=Vegetable}

        try {
            for (Entry<String, String> item : foodItemTypeMap.entrySet()) {
                if (item.getKey()
                    .equals("Potato")) {
                    foodItemTypeMap.remove(item.getKey());
                }
            }
        } catch (ConcurrentModificationException e) {
            System.out.println("Exception occured while updating map: " + e.toString());
        }

        foodItemTypeMap.entrySet()
            .removeIf(entry -> entry.getKey()
                .equals("Grape"));
        // Current Map Status: {Carrot=Vegetable, Mango=Fruit, Spinach=Vegetable}

        Iterator<Entry<String, String>> iterator = foodItemTypeMap.entrySet()
            .iterator();
        while (iterator.hasNext()) {
            if (iterator.next()
                .getKey()
                .equals("Carrot"))
                iterator.remove();
        }
        // Current Map Status: {Mango=Fruit, Spinach=Vegetable}

        // Use ConcurrentHashMap
        ConcurrentHashMap<String, String> foodItemTypeConcMap = new ConcurrentHashMap<>();
        foodItemTypeConcMap.put("Apple", "Fruit");
        foodItemTypeConcMap.put("Grape", "Fruit");
        foodItemTypeConcMap.put("Mango", "Fruit");
        foodItemTypeConcMap.put("Carrot", "Vegetable");
        foodItemTypeConcMap.put("Potato", "Vegetable");
        foodItemTypeConcMap.put("Spinach", "Vegetable");

        for (Entry<String, String> item : foodItemTypeConcMap.entrySet()) {
            if (item.getKey() != null && item.getKey()
                .equals("Potato")) {
                foodItemTypeConcMap.remove(item.getKey());
            }
        }

        // foodItemTypeConcMap : {Apple=Fruit, Carrot=Vegetable, Grape=Fruit, Mango=Fruit, Spinach=Vegetable}

    }

}
