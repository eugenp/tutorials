package com.baeldung.booleantoint;

import org.apache.commons.lang3.BooleanUtils;

public class BooleanToInt {
    public static int booleanPrimitiveToInt(boolean foo) {
        int bar = 0;
        if (foo) {
            bar = 1;
        }
        return bar;
    }

    public static int booleanPrimitiveToIntTernary(boolean foo) {
        return (foo) ? 1 : 0;
    }

    public static int booleanObjectToInt(boolean foo) {
        return Boolean.compare(foo, false);
    }

    public static int booleanObjectToIntInverse(boolean foo) {
        return Boolean.compare(foo, true) + 1;
    }

    public static int booleanObjectMethodToInt(Boolean foo) {
        return foo.compareTo(false);
    }

    public static int booleanObjectMethodToIntInverse(Boolean foo) {
        return foo.compareTo(true) + 1;
    }

    public static int booleanUtilsToInt(Boolean foo) {
        return BooleanUtils.toInteger(foo);
    }

    public static int bitwiseBooleanToInt(Boolean foo) {
        return (Boolean.hashCode(foo) >> 1) & 1;
    }
}

