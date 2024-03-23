package com.baeldung.convertphonenumberinwordstonumberwithjava;

import java.util.Arrays;
import java.util.List;

public class UseIfElseToConvertPhoneNumberInWordToNumber {

    public static String ConvertPhoneNumberInWordToNumber(String phoneNumberInWord) {

        List<String> phoneNumberInWordArray = Arrays.asList(phoneNumberInWord.split(" "));
        String convertedNumber = "";
        int temp;

        for (int i = 0; i < phoneNumberInWordArray.size(); i++) {
            temp = mapModifiers(phoneNumberInWordArray.get(i));
            if (temp == -1)
                convertedNumber += mapIndividualDigits(phoneNumberInWordArray.get(i));
            else {
                for (int j = 0; j < temp; j++)
                    convertedNumber += mapIndividualDigits(phoneNumberInWordArray.get(i + 1));
                i++;
            }
        }

        return convertedNumber;
    }
    public static int mapModifiers(String word) {
        if (word.equals("double"))
            return 2;
        else if (word.equals("triple"))
            return 3;
        else if (word.equals("quad"))
            return 4;
        else if (word.equals("penta"))
            return 5;
        else if (word.equals("hexa"))
            return 6;
        else if (word.equals("hepta"))
            return 7;
        else if (word.equals("octa"))
            return 8;
        else if (word.equals("nona"))
            return 9;
        else if (word.equals("deca"))
            return 10;

        return -1;
    }

    public static int mapIndividualDigits(String word) {
        if (word.equals("zero"))
            return 0;
        else if (word.equals("one"))
            return 1;
        else if (word.equals("two"))
            return 2;
        else if (word.equals("three"))
            return 3;
        else if (word.equals("four"))
            return 4;
        else if (word.equals("five"))
            return 5;
        else if (word.equals("six"))
            return 6;
        else if (word.equals("seven"))
            return 7;
        else if (word.equals("eight"))
            return 8;
        else if (word.equals("nine"))
            return 9;

        return -1;
    }
}
