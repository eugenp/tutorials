package com.baeldung.mapandhashmap;

import com.baeldung.mapandhashmap.printer.HashMapPrinter;
import com.baeldung.mapandhashmap.printer.MapPrinter;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Main {
    public static void main(String[] args) {
        Map<String, String> map = new HashMap<>();
        HashMap<String, String> hashMap = new HashMap<>();
        TreeMap<String, String> treeMap = new TreeMap<>();

        HashMapPrinter hashMapPrinter = new HashMapPrinter();
        hashMapPrinter.printMap(hashMap);
//        hashMapPrinter.printMap(treeMap); Compile time error
//        hashMapPrinter.printMap(map); Compile time error

        MapPrinter mapPrinter = new MapPrinter();
        mapPrinter.printMap(hashMap);
        mapPrinter.printMap(treeMap);
        mapPrinter.printMap(map);
    }
}
