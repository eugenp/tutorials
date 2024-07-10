package com.baeldung.lombok.extensionmethod;

import java.util.List;

public class ListUtils {

    public static int sum(List<Integer> listInt) {
        return listInt.stream()
            .mapToInt(Integer::intValue)
            .sum();
    }
}
