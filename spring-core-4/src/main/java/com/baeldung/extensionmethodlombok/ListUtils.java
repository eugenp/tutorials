package com.baeldung.extensionmethodlombok;

import java.util.List;

public class ListUtils {

    public static int sum(List<Integer> list) {
        return list.stream()
            .mapToInt(Integer::intValue)
            .sum();
    }
}
