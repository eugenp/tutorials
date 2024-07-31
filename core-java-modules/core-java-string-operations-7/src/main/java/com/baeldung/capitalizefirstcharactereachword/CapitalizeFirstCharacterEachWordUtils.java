package com.baeldung.capitalizefirstcharactereachword;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

class CapitalizeFirstCharacterEachWordUtils {

    static String usingCharacterToUpperCaseMethod(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        return Arrays.stream(input.split("\\s+"))
          .map(word -> Character.toUpperCase(word.charAt(0)) + word.substring(1))
          .collect(Collectors.joining(" "));
    }

    static String usingStringToUpperCaseMethod(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        return Arrays.stream(input.split("\\s+"))
          .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1))
          .collect(Collectors.joining(" "));
    }

    static String usingStringUtilsClass(String input) {
        if (input == null || input.isEmpty()) {
            return null;
        }

        return Arrays.stream(input.split("\\s+"))
          .map(StringUtils::capitalize)
          .collect(Collectors.joining(" "));
    }

}
