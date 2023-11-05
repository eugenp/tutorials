package com.baeldung.genericnumberscomparator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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

    public int compareTo(Integer int1, Integer int2) {
        return int1.compareTo(int2);
    }

    @Test
    void givenNumbers_whenUseCompareTo_thenWillExecuteComparison() {
        assertEquals(-1, compareTo(5, 7));
    }

    Map<Class<? extends Number>, BiFunction<Number, Number, Integer>> comparisonMap = new HashMap<>();

    public int compareUsingMap(Number num1, Number num2) {
        comparisonMap.put(Integer.class, (a, b) -> ((Integer) num1).compareTo((Integer) num2));

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

    interface NumberComparatorFactory {
        Comparator<Number> getComparator();
    }

    class IntegerComparatorFactory implements NumberComparatorFactory {
        @Override
        public Comparator<Number> getComparator() {
            return (num1, num2) -> ((Integer) num1).compareTo((Integer) num2);
        }
    }

    @Test
    void givenNumbers_whenUseComparatorFactory_thenWillExecuteComparison() {
        NumberComparatorFactory factory = new IntegerComparatorFactory();
        Comparator<Number> comparator = factory.getComparator();
        assertEquals(-1, comparator.compare(5, 7));
    }

    Function<Number, Double> toDouble = Number::doubleValue;
    BiPredicate<Number, Number> isEqual = (num1, num2) -> toDouble.apply(num1)
        .equals(toDouble.apply(num2));

    @Test
    void givenNumbers_whenUseIsEqual_thenWillExecuteComparison() {
        assertEquals(true, isEqual.test(5, 5.0));
    }

    private Number someNumber = 5;
    private Number anotherNumber = 5.0;

    Optional<Number> optNum1 = Optional.ofNullable(someNumber);
    Optional<Number> optNum2 = Optional.ofNullable(anotherNumber);
    int comparisonResult = optNum1.flatMap(n1 -> optNum2.map(n2 -> Double.compare(n1.doubleValue(), n2.doubleValue())))
        .orElse(0);

    @Test
    void givenNumbers_whenUseComparisonResult_thenWillExecuteComparison() {
        assertEquals(0, comparisonResult);
    }

    private boolean someCondition = true;
    Function<Number, ?> dynamicFunction = someCondition ? Number::doubleValue : Number::intValue;
    Comparator<Number> dynamicComparator = (num1, num2) -> ((Comparable) dynamicFunction.apply(num1)).compareTo(dynamicFunction.apply(num2));

    @Test
    void givenNumbers_whenUseDynamicComparator_thenWillExecuteComparison() {
        assertEquals(0, dynamicComparator.compare(5, 5.0));
    }

}
