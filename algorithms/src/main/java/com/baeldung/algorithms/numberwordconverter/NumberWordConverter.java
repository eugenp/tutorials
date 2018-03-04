package com.baeldung.algorithms.numberwordconverter;

import java.math.BigDecimal;

import pl.allegro.finance.tradukisto.MoneyConverters;

public class NumberWordConverter {

    public static final String INVALID_INPUT_GIVEN = "Invalid input given";

    public static final String[] ones = { "", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fifteen", "sixteen", "seventeen", "eighteen", "nineteen" };

    public static final String[] tens = { "", // 0
            "", // 1
            "twenty", // 2
            "thirty", // 3
            "forty", // 4
            "fifty", // 5
            "sixty", // 6
            "seventy", // 7
            "eighty", // 8
            "ninety" // 9
    };

    public static String getMoneyIntoWords(String input) {
        MoneyConverters converter = MoneyConverters.ENGLISH_BANKING_MONEY_VALUE;
        return converter.asWords(new BigDecimal(input));
    }

    public static String getMoneyIntoWords(final double money) {
        long dollar = (long) money;
        long cents = Math.round((money - dollar) * 100);
        String dollarPart = formatOutput(convert(dollar), dollar, "dollar");
        String centsPart = "";
        if (cents > 0) {
            if (dollarPart.length() > 0) {
                centsPart = " and ";
            }
            centsPart += formatOutput(convert(cents), cents, "cent");
        }
        return dollarPart + centsPart;
    }

    private static String convert(final long n) {
        if (n < 0) {
            return INVALID_INPUT_GIVEN;
        }
        if (n < 20) {
            return ones[(int) n];
        }
        if (n < 100) {
            return tens[(int) n / 10] + ((n % 10 != 0) ? " " : "") + ones[(int) n % 10];
        }
        if (n < 1000) {
            return ones[(int) n / 100] + " hundred" + ((n % 100 != 0) ? " " : "") + convert(n % 100);
        }
        if (n < 1_000_000) {
            return convert(n / 1000) + " thousand" + ((n % 1000 != 0) ? " " : "") + convert(n % 1000);
        }
        if (n < 1_000_000_000) {
            return convert(n / 1_000_000) + " million" + ((n % 1_000_000 != 0) ? " " : "") + convert(n % 1_000_000);
        }
        return convert(n / 1_000_000_000) + " billion" + ((n % 1_000_000_000 != 0) ? " " : "") + convert(n % 1_000_000_000);
    }

    private static String formatOutput(String input, long number, String type) {
        if (input.equals(INVALID_INPUT_GIVEN)) {
            return INVALID_INPUT_GIVEN;
        } else if (input.length() > 0) {
            if (number == 1) {
                return input + " " + type;
            } else {
                return input + " " + type + "s";
            }
        }
        return "";
    }
}