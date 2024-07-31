package com.baeldung.convertLongToInt;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.function.Function;

import com.google.common.primitives.Ints;

public class ConvertLongToInt {

    static Function<Long, Integer> convert = val -> Optional.ofNullable(val)
        .map(Long::intValue)
        .orElse(null);

    public static int longToIntCast(long number) {
        return (int) number;
    }

    public static int longToIntJavaWithMath(long number) {
        return Math.toIntExact(number);
    }

    public static int longToIntJavaWithLambda(long number) {
        return convert.apply(number);
    }

    public static int longToIntBoxingValues(long number) {
        return Long.valueOf(number)
            .intValue();
    }

    public static int longToIntGuava(long number) {
        return Ints.checkedCast(number);
    }

    public static int longToIntGuavaSaturated(long number) {
        return Ints.saturatedCast(number);
    }

    public static int longToIntWithBigDecimal(long number) {
        return new BigDecimal(number).intValueExact();
    }

}
