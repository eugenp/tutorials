package com.baeldung.stringtoint;

import java.util.Optional;
import org.apache.commons.lang3.math.NumberUtils;

public class StringToIntEncapsulation {
    
    private StringToIntEncapsulation() {
    }

    public static Integer convertStringToIntUsingIntegerParseInt(String input){
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            // log or handle the error
            return null; // or Integer.MIN_VALUE, or some other default value
        }
    }

    public static Integer convertStringToIntUsingIntegerValueOf(String input){
        try {
            return Integer.valueOf(input);
        } catch (NumberFormatException e) {
            // log or handle the error
            return null; // or Integer.MIN_VALUE, or some other default value
        }
    }

    public static Integer convertStringToIntUsingIntegerDecode(String input){
        try {
            return Integer.decode(input);
        } catch (Exception e) {
            // log or handle the error
            return null; // or Integer.MIN_VALUE, or some other default value
        }
    }

    public static Integer converStringToIntUsingOptional(String input){
        Optional<Integer> parsedInt;
        try {
            parsedInt = Optional.of(Integer.parseInt(input));
        } catch (Exception e) {
            // log or handle the error
            parsedInt = Optional.empty();
        }
        return parsedInt.orElse(null);
    }

    public static int convertStringToIntUsingNumberUtils(String input){
        //returns Integer.MIN_VALUE as the default value if conversion fails
        return NumberUtils.toInt(input, Integer.MIN_VALUE);
    }
}
