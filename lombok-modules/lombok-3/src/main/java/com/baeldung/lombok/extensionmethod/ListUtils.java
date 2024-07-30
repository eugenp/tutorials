package com.baeldung.lombok.extensionmethod;

import java.util.List;

public class ListUtils {

    public static int sum(List<? extends Number> list) {
        return list.stream()
            .mapToInt(Number::intValue)
            .sum();
    }
}
