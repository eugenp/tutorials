package com.baeldung.streams.reduce.utilities;

import java.util.List;
import java.util.function.BiFunction;
import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class NumberUtils {

    private static final Logger LOGGER = Logger.getLogger(NumberUtils.class.getName());

    public static int divideListElements(List<Integer> values, Integer divider) {
        return values.stream()
                .reduce(0, (a, b) -> {
                    try {
                        return a / divider + b / divider;
                    } catch (ArithmeticException e) {
                        LOGGER.log(Level.INFO, "Arithmetic Exception: Division by Zero");
                    }
                    return 0;
                });
    }
    
    public static int divideListElementsWithExtractedTryCatchBlock(List<Integer> values, int divider) {
        return values.stream().reduce(0, (a, b) -> divide(a, divider) + divide(b, divider));
    }
    
    public static int divideListElementsWithApplyFunctionMethod(List<Integer> values, int divider) {
        BiFunction<Integer, Integer, Integer> division = (a, b) -> a / b;
        return values.stream().reduce(0, (a, b) -> applyFunction(division, a, divider) + applyFunction(division, b, divider));
    }
    
    private static int divide(int value, int factor) {
        int result = 0;
        try {
            result = value / factor;
        } catch (ArithmeticException e) {
            LOGGER.log(Level.INFO, "Arithmetic Exception: Division by Zero");
        }
        return result;
    }
    
    private static int applyFunction(BiFunction<Integer, Integer, Integer> function, int a, int b) {
        try {
            return function.apply(a, b);
        }
        catch(Exception e) {
            LOGGER.log(Level.INFO, "Exception thrown!");
        }
        return 0;
    }
}
