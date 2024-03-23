package com.baeldung.convertphonenumberinwordstonumberwithjava;

import java.util.HashMap;
import java.util.Map;

public class UseHashMapToConvertPhoneNumberInWordToNumber {

    public static String ConvertPhoneNumberInWordToNumber(String phoneNumberInWord) {

        Map<String, Integer> modifiers = new HashMap<>();
        Map<String, Integer> digits = new HashMap<>();

        mapModifiers(modifiers);
        mapIndividualDigits(digits);

        String convertedNumber = "";
        int n = 1;

        String[] words = phoneNumberInWord.split("\\s+");
        for (String word : words) {
            if (modifiers.containsKey(word)) {
                n *= modifiers.get(word);
            } else {
                for (int i = 0; i < n; i++) {
                    convertedNumber += digits.get(word);
                }
                n = 1;
            }
        }
        return convertedNumber;
    }

    public static Map<String, Integer> mapModifiers(Map<String, Integer> modifiers) {
        modifiers.put("double", 2);
        modifiers.put("triple", 3);
        modifiers.put("quad", 4);
        modifiers.put("penta", 5);
        modifiers.put("hexa", 6);
        modifiers.put("hepta", 7);
        modifiers.put("octa", 8);
        modifiers.put("nona", 9);
        modifiers.put("deca", 10);

        return modifiers;
    }


    public static Map<String, Integer> mapIndividualDigits(Map<String, Integer> digits) {
        digits.put("zero", 0);
        digits.put("one", 1);
        digits.put("two", 2);
        digits.put("three", 3);
        digits.put("four", 4);
        digits.put("five", 5);
        digits.put("six", 6);
        digits.put("seven", 7);
        digits.put("eight", 8);
        digits.put("nine", 9);

        return digits;
    }

}
