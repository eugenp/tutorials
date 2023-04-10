package com.baeldung.stringtoint;

import java.util.Optional;
import org.apache.commons.lang3.math.NumberUtils;

public class StringToIntConverter {

    private StringToIntConverter() {
    }

    public static Integer convertStringToIntUsingIntegerParseInt(String input){
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // log or handle the error
            return Integer.MIN_VALUE;
        }
    }

    public static Integer convertStringToIntUsingIntegerValueOf(String input){
        try {
            return Integer.valueOf(input);
        } catch (NumberFormatException e) {
            // log or handle the error
            return Integer.MIN_VALUE;
        }
    }

    public static Integer convertStringToIntUsingIntegerDecode(String input){
        try {
            return Integer.decode(input);
        } catch (Exception e) {
            // log or handle the error
            return Integer.MIN_VALUE;
        }
    }

    public static Integer convertStringToIntUsingOptional(String input){
        Optional<Integer> parsedInt;
        try {
            parsedInt = Optional.of(Integer.parseInt(input));
        } catch (Exception e) {
            // log or handle the error
            parsedInt = Optional.empty();
        }
        return parsedInt.orElse(Integer.MIN_VALUE);
    }

    public static int convertStringToIntUsingNumberUtils(String input, Integer defaultValue){
        return NumberUtils.toInt(input, defaultValue);
    }
}

