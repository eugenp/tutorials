package com.baeldung.algorithms.firstnonrepeating;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirstNonRepeatingElement {
    public static Integer findFirstNonRepeatingUsingForLoop(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            int current = list.get(i);
            boolean isRepeating = false;
            for (int j = 0; j < list.size(); j++) {
                if (i != j && current == list.get(j)) {
                    isRepeating = true;
                    break;
                }
            }
            if (!isRepeating) {
                return current;
            }
        }
        return null;
    }

    public static Integer findFirstNonRepeatedElementUsingIndex(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            if (list.indexOf(list.get(i)) == list.lastIndexOf(list.get(i))) {
                return list.get(i);
            }
        }
        return null;
    }

    public static Integer findFirstNonRepeatingUsingHashMap(List<Integer> list) {
        Map<Integer, Integer> counts = new HashMap<>();
        for (int num : list) {
            counts.put(num, counts.getOrDefault(num, 0) + 1);
        }
        for (int num : list) {
            if (counts.get(num) == 1) {
                return num;
            }
        }
        return null;
    }

    public static Integer findFirstNonRepeatingUsingArray(List<Integer> list) {
        int maxElement = Collections.max(list);
        int[] frequency = new int[maxElement + 1];
        for (int num : list) {
            frequency[num]++;
        }
        for (int num : list) {
            if (frequency[num] == 1) {
                return num;
            }
        }
        return null;
    }
}
