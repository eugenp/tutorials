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

        String snumber = Long.toString(number);

        // pad with "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        // XXXnnnnnnnnn
        int billions = Integer.parseInt(snumber.substring(0, 3));
        // nnnXXXnnnnnn
        int millions = Integer.parseInt(snumber.substring(3, 6));
        // nnnnnnXXXnnn
        int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
        // nnnnnnnnnXXX
        int thousands = Integer.parseInt(snumber.substring(9, 12));

        String tradBillions;
        switch (billions) {
        case 0:
            tradBillions = "";
            break;
        case 1:
            tradBillions = convertLessThanOneThousand(billions) + " billion ";
            break;
        default:
            tradBillions = convertLessThanOneThousand(billions) + " billion ";
        }
        String result = tradBillions;

        String tradMillions;
        switch (millions) {
        case 0:
            tradMillions = "";
            break;
        case 1:
            tradMillions = convertLessThanOneThousand(millions) + " million ";
            break;
        default:
            tradMillions = convertLessThanOneThousand(millions) + " million ";
        }
        result = result + tradMillions;

        String tradHundredThousands;
        switch (hundredThousands) {
        case 0:
            tradHundredThousands = "";
            break;
        case 1:
            tradHundredThousands = "one thousand ";
            break;
        default:
            tradHundredThousands = convertLessThanOneThousand(hundredThousands) + " thousand ";
        }
        result = result + tradHundredThousands;

        String tradThousand;
        tradThousand = convertLessThanOneThousand(thousands);
        result = result + tradThousand;

        // remove extra spaces
        return result.replaceAll("^\\s+", "")
            .replaceAll("\\b\\s{2,}\\b", " ");
    }

    private String convertLessThanOneThousand(int number) {
        String soFar;

        if (number % 100 < 20) {
            soFar = ones[number % 100];
            number /= 100;
        } else {
            soFar = ones[number % 10];
            number /= 10;

            soFar = tens[number % 10] + soFar;
            number /= 10;
        }
        if (number == 0)
            return soFar;
        return ones[number] + " hundred" + soFar;
    }

}
