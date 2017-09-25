package com.baeldung.streamex;

import one.util.streamex.DoubleStreamEx;
import one.util.streamex.IntStreamEx;

public class MathUtil {

    public double[] getDiffBetweenPairs(double... numbers) {
        return DoubleStreamEx.of(numbers).pairMap((a, b) -> b - a).toArray();
    }

    public short[] multiply(short[] src, short multiplier) {
        return IntStreamEx.of(src).map(x -> x * multiplier).toShortArray();
    }

}
