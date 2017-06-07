package com.baeldung.map.iteration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MapIteration {

    public List<String> iterateUsingEntrySet(Map<String, Integer> map) {
        List<String> mapKeyValueList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            mapKeyValueList.add(entry.getKey() + ":" + entry.getValue());
        }
        return mapKeyValueList;
    }

    public List<String> iterateUsingLambda(Map<String, Integer> map) {
        List<String> mapKeyValueList = new ArrayList<>();
        map.forEach((k, v) -> mapKeyValueList.add(k + ":" + v));
        return mapKeyValueList;
    }

    public List<String> iterateUsingIteratorAndEntry(Map<String, Integer> map) {
        ArrayList<String> mapKeyValueList = new ArrayList<>();
        Iterator<Map.Entry<String, Integer>> iterator = map.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Integer> pair = iterator.next();
            mapKeyValueList.add(pair.getKey() + ":" + pair.getValue());
        }

        return mapKeyValueList;
    }

    public List<String> iterateUsingKeySetAndForeach(Map<String, Integer> map) {
        List<String> mapKeyValueList = new ArrayList<>();
        for (String key : map.keySet()) {
            mapKeyValueList.add(key + ":" + map.get(key));
        }
        return mapKeyValueList;
    }

    public List<String> iterateUsingStreamAPI(Map<String, Integer> map) {
        ArrayList<String> mapKeyValueList = new ArrayList<>();
        map.entrySet().stream().forEach(e -> mapKeyValueList.add(e.getKey() + ":" + e.getValue()));
        return mapKeyValueList;
    }

    public ArrayList<String> iterateKeys(Map<String, Integer> map) {
        ArrayList<String> mapKeyList = new ArrayList<>();
        for (String key : map.keySet()) {
            mapKeyList.add(key);
        }
        return mapKeyList;
    }

}