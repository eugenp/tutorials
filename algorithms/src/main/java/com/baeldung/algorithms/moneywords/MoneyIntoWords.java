package com.baeldung.algorithms.moneywords;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import pl.allegro.finance.tradukisto.MoneyConverters;

public class MoneyIntoWords {

    private final String[] tens = { "", " ten", " twenty", " thirty", " forty", " fifty", " sixty", " seventy", " eighty", " ninety" };

    private final String[] ones = { "", " one", " two", " three", " four", " five", " six", " seven", " eight", " nine", " ten", " eleven", " twelve", " thirteen", " fourteen", " fifteen", " sixteen", " seventeen", " eighteen", " nineteen" };

    public String getMoneyIntoWords(String input) {
        MoneyConverters converter = MoneyConverters.ENGLISH_BANKING_MONEY_VALUE;
        return converter.asWords(new BigDecimal(input));
    }

    public String getMoneyIntoWords(double money) {
        StringBuilder result = new StringBuilder("");
        if (money < 0) {
            return result.toString();
        }
        if (money == 0) {
            return result.append("zero dollars")
                .toString();
        }
        long dollars = (long) money;
        long cents = (long) (money * 100 - dollars * 100);
        return addDollarsCentsWords(result, dollars, cents);
    }

    private String addDollarsCentsWords(StringBuilder result, long dollars, long cents) {
        long[] values = { dollars, cents };
        for (int i = 0; i < values.length; i++) {
            if (values[i] != 0) {
                if (result.length() != 0 && i == 1) {
                    result.append(" and ");
                }
                result.append(convert(values[i]));
                if (i == 0) {
                    if (dollars == 1) {
                        result.append(" dollar");
                    } else {
                        result.append(" dollars");
                    }
                } else {
                    if (cents == 1) {
                        result.append(" cent");
                    } else {
                        result.append(" cents");
                    }
                }
            }
        }
        return result.toString()
            .trim();
    }

    private String convert(long number) {
        // 0 to 999 999 999 999
        if (number == 0) {
            return "zero";
        }

        String stringNumber = Long.toString(number);

        // Pad with "0" and avoid string exceptions if the number is smaller than a billion
        String mask = "000000000000";
        DecimalFormat decimalFormat = new DecimalFormat(mask);
        stringNumber = decimalFormat.format(number);

        // Get the number of billions from the format: XXXnnnnnnnnn
        int billions = Integer.parseInt(stringNumber.substring(0, 3));

        // nnnXXXnnnnnn
        int millions = Integer.parseInt(stringNumber.substring(3, 6));

        // nnnnnnXXXnnn
        int hundredThousands = Integer.parseInt(stringNumber.substring(6, 9));

        //  nnnnnnnnnXXX
        int thousands = Integer.parseInt(stringNumber.substring(9, 12));

        String formattedBillions = formatExpression(billions, "billion");

        String formattedMillions = formatExpression(millions, "million");

        String formattedHundredThousand = formatExpression(hundredThousands, "thousand");

        String tradThousand = convertLessThanOneThousand(thousands);

        String result = formattedBillions + formattedMillions + formattedHundredThousand + tradThousand;

        return result.trim();
    }

    private String formatExpression(int number, String type) {
        String expression;
        switch (number) {
        case 0:
            expression = "";
            break;
        case 1:
            if (type == "thousand") {
                expression = "one thousand ";
            } else {
                expression = convertLessThanOneThousand(number) + " " + type + " ";
            }
            break;
        default:
            expression = convertLessThanOneThousand(number) + " " + type + " ";
        }
        return expression;
    }

    private String convertLessThanOneThousand(int number) {
        String result;
        if (number % 100 >= 20) {
            // get the number of ones as words
            result = ones[number % 10];
            // remove the last digit
            number /= 10;
            // get the number of tens as words
            result = tens[number % 10] + result;
            // remove the last digit
            number /= 10;
        } else {
            // get the number of ones as words
            result = ones[number % 100];
            // remove the last two digits
            number /= 100;
        }
        if (number == 0) {
            return result;
        }
        // return the number of hundreds
        return ones[number] + " hundred" + result;
    }

}
