package com.baeldung.romannumerals;

class RomanArabicConverter {

    public static int romanToArabic(String input) {
        String romanNumeral = input.toUpperCase();
        int result = 0;
        RomanNumeral[] romanNumerals = RomanNumeral.values();

        int i = 0;

        while (romanNumeral.length() > 0 && i < romanNumerals.length) {
            RomanNumeral symbol = romanNumerals[i];
            if (romanNumeral.startsWith(symbol.name())) {
                result += symbol.getValue();
                romanNumeral = romanNumeral.substring(symbol.name().length());
            } else {
                i++;
            }
        }
        if (romanNumeral.length() > 0) {
            throw new IllegalArgumentException(input + " cannot be converted to a Roman Numeral");
        }

        return result;
    }

    public static String arabicToRoman(int number) {
        if (number <=0 || number > 4000) {
            throw new IllegalArgumentException(number + " is not in range (0,4000>");
        }
        RomanNumeral[] romanNumerals = RomanNumeral.values();
        int i = 0;
        StringBuilder sb = new StringBuilder();

        while (number > 0 && i < romanNumerals.length) {
            RomanNumeral currentSymbol = romanNumerals[i];
            if (currentSymbol.getValue() <= number) {
                sb.append(currentSymbol.name());
                number -= currentSymbol.getValue();
            } else {
                i++;
            }
        }
        return sb.toString();
    }
}