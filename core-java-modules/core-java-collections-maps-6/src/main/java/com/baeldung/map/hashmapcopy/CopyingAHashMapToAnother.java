package com.baeldung.map.hashmapcopy;

import java.util.Map;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;

public class CopyingAHashMapToAnother {
    public Map<String, String> copyByIteration(Map<String, String> sourceMap, Map<String, String> targetMap) {
        for (Map.Entry<String, String> entry : sourceMap.entrySet()) {
            if (!targetMap.containsKey(entry.getKey())) {
                targetMap.put(entry.getKey(), entry.getValue());
            }
        }
        return targetMap;
    }

    public Map<String, String> copyUsingPutAll(Map<String, String> sourceMap, Map<String, String> targetMap) {
        sourceMap.keySet()
          .removeAll(targetMap.keySet());
        targetMap.putAll(sourceMap);
        return targetMap;
    }

    public Map<String, String> copyUsingPutIfAbsent(Map<String, String> sourceMap, Map<String, String> targetMap) {
        for (Map.Entry<String, String> entry : sourceMap.entrySet()) {
            targetMap.putIfAbsent(entry.getKey(), entry.getValue());
        }
        return targetMap;
    }

    public Map<String, String> copyUsingPutIfAbsentForEach(Map<String, String> sourceMap, Map<String, String> targetMap) {
        sourceMap.forEach(targetMap::putIfAbsent);
        return targetMap;
    }

    public Map<String, String> copyUsingMapMerge(Map<String, String> sourceMap, Map<String, String> targetMap) {
        sourceMap.forEach((key, value) -> targetMap.merge(key, value, (oldVal, newVal) -> oldVal));
        return targetMap;
    }

    public Map<String, String> copyUsingGuavaMapDifference(Map<String, String> sourceMap, Map<String, String> targetMap) {
        MapDifference<String, String> differenceMap = Maps.difference(sourceMap, targetMap);
        targetMap.putAll(differenceMap.entriesOnlyOnLeft());
        return targetMap;
    }
}
