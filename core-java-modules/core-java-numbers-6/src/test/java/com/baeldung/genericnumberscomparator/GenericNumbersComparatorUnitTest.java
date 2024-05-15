package com.baeldung.genericnumberscomparator;

import static org.assertj.core.api.Assertions.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Comparator;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

class GenericNumbersComparatorUnitTest {

    public int compareDouble(Number num1, Number num2) {
        return Double.compare(num1.doubleValue(), num2.doubleValue());
    }

    @Test
    void givenNumbers_whenUseCompareDouble_thenWillExecuteComparison() {
        assertEquals(0, compareDouble(5, 5.0));
    }

    // we create a method that compares Integer, but this could also be done for other types e.g. Double, BigInteger
    public int compareTo(Integer int1, Integer int2) {
        return int1.compareTo(int2);
    }

    @Test
    void givenNumbers_whenUseCompareTo_thenWillExecuteComparison() {
        assertEquals(-1, compareTo(5, 7));
    }

    // for this example, we create a function that compares Integer, but this could also be done for other types e.g. Double, BigInteger
    Map<Class<? extends Number>, BiFunction<Number, Number, Integer>> comparisonMap = Map.ofEntries(entry(Integer.class, (num1, num2) -> ((Integer) num1).compareTo((Integer) num2)));

    public int compareUsingMap(Number num1, Number num2) {
        return comparisonMap.get(num1.getClass())
            .apply(num1, num2);
    }

    @Test
    void givenNumbers_whenUseCompareUsingMap_thenWillExecuteComparison() {
        assertEquals(-1, compareUsingMap(5, 7));
    }

    public interface NumberComparator {
        int compare(Number num1, Number num2);
    }

    @Test
    void givenNumbers_whenUseProxy_thenWillExecuteComparison() {
        NumberComparator proxy = (NumberComparator) Proxy.newProxyInstance(NumberComparator.class.getClassLoader(), new Class[] { NumberComparator.class },
            (p, method, args) -> Double.compare(((Number) args[0]).doubleValue(), ((Number) args[1]).doubleValue()));
        assertEquals(0, proxy.compare(5, 5.0));
    }

    public int compareUsingReflection(Number num1, Number num2) throws Exception {
        Method method = num1.getClass()
            .getMethod("compareTo", num1.getClass());
        return (int) method.invoke(num1, num2);
    }

    @Test
    void givenNumbers_whenUseCompareUsingReflection_thenWillExecuteComparison() throws Exception {
        assertEquals(-1, compareUsingReflection(5, 7));
    }

    Function<Number, Double> toDouble = Number::doubleValue;
    BiPredicate<Number, Number> isEqual = (num1, num2) -> toDouble.apply(num1)
        .equals(toDouble.apply(num2));

    @Test
    void givenNumbers_whenUseIsEqual_thenWillExecuteComparison() {
        assertEquals(true, isEqual.test(5, 5.0));
    }

    private boolean someCondition;
    Function<Number, ?> dynamicFunction = someCondition ? Number::doubleValue : Number::intValue;
    Comparator<Number> dynamicComparator = (num1, num2) -> ((Comparable) dynamicFunction.apply(num1)).compareTo(dynamicFunction.apply(num2));

    @Test
    void givenNumbers_whenUseDynamicComparator_thenWillExecuteComparison() {
        assertEquals(0, dynamicComparator.compare(5, 5.0));
    }

}
