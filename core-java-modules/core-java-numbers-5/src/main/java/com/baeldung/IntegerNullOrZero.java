package com.baeldung;

import java.util.Optional;
import org.apache.commons.lang3.ObjectUtils;

public class IntegerNullOrZero {
    private IntegerNullOrZero() {
        throw new RuntimeException("This class cannot be instantiated.");
    }

    public static boolean usingStandardWay(Integer num) {
        return num == null || num == 0;
    }

    public static boolean usingTernaryOperator(Integer num) {
        return 0 == (num == null ? 0 : num);
    }

    public static boolean usingOptional(Integer num) {
        return Optional.ofNullable(num).orElse(0) == 0;
    }

    public static boolean usingObjectUtils(Integer num) {
        return ObjectUtils.defaultIfNull(num, 0) == 0;
    }
}