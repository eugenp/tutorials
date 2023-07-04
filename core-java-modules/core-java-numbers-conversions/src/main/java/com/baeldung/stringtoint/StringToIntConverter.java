package com.baeldung.stringtoint;

import java.util.Optional;
import org.apache.commons.lang3.math.NumberUtils;

public class StringToIntConverter {
    Optional<Integer> convertStringToIntUsingIntegerParseInt(String input){
        try {
            return Optional.of(Integer.parseInt(input));
        } catch (NumberFormatException e) {
            // log or handle the error
            return Optional.empty();
        }
    }

    Optional<Integer> convertStringToIntUsingIntegerValueOf(String input){
        try {
            return Optional.of(Integer.valueOf(input));
        } catch (NumberFormatException e) {
            // log or handle the error
            return Optional.empty();
        }
    }

    Optional<Integer> convertStringToIntUsingIntegerDecode(String input){
        try {
            return Optional.of(Integer.decode(input));
        } catch (Exception e) {
            // log or handle the error
            return Optional.empty();
        }
    }

    int convertStringToIntUsingNumberUtils(String input, Integer defaultValue){
        return NumberUtils.toInt(input, defaultValue);
    }
}

