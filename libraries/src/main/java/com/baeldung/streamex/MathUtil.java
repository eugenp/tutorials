package com.baeldung.streamex;

import java.util.function.BinaryOperator;

import one.util.streamex.DoubleStreamEx;
import one.util.streamex.IntStreamEx;
import one.util.streamex.StreamEx;

public class MathUtil {

    public double[] m(double... numbers) {
        return DoubleStreamEx.of(numbers).pairMap((a, b) -> b - a).toArray();
    }

    public short[] multiply(short[] src, short multiplier) {
        return IntStreamEx.of(src).map(x -> x * multiplier).toShortArray();
    }

    public <T> StreamEx<T> scanLeft(StreamEx<T> input, BinaryOperator<T> operator) {
        return input.headTail(
                (head, tail) -> scanLeft(tail.mapFirst(cur -> operator.apply(head, cur)), operator).prepend(head));
    }

}
