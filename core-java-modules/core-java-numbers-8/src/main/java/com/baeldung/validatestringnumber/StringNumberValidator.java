package com.baeldung.validatestringnumber;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import org.apache.commons.lang3.math.NumberUtils;

public class StringNumberValidator {

    private static final Pattern NUMBER_PATTERN = Pattern.compile("^[-+]?[0-9]*\\.?[0-9]+([eE][-+]?[0-9]+)?$");

    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }

        int decimalCount = 0;
        boolean hasDigits = false;

        for (char c : str.toCharArray()) {
            if (c == '.') {
                decimalCount++;
                if (decimalCount > 1) {
                    return false;
                }
            }
            else if (Character.isDigit(c)) {
                hasDigits = true;
            } else {
                return false;
            }
        }
        return hasDigits && decimalCount <= 1;
    }

    public static boolean isInteger(String str) {
        if (str == null) {
            return false;
        }

        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDouble(String str) {
        if (str == null) {
            return false;
        }

        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isBigDecimal(String str) {
        if (str == null) {
            return false;
        }

        try {
            new BigDecimal(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidNumberRegex(String str) {
        return str != null && NUMBER_PATTERN.matcher(str)
            .matches();
    }
}
