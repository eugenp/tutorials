package com.baeldung.checkforvowels;

import java.util.Scanner;
import java.util.regex.Pattern;

public class CheckForVowels {
    private static final String VOWELS = "aeiouAEIOU";
    private static final String REGEX = "[aeiouAEIOU]";

    public static void main(String[] args) {

        Scanner consoleInput = new Scanner(System.in);
        System.out.println("Enter character");

        char inputVal = consoleInput.nextLine()
            .charAt(0);
        // using 1st method
        if (checkIfVowelUsingIndexOf(inputVal)) {
            System.out.println("Vowel");
        } else {
            System.out.println("Consonant");
        }
        // Using 2nd method
        if (checkIfVowelUsingSwitchCase(inputVal)) {
            System.out.println("Vowel");
        } else {
            System.out.println("Consonant");
        }
        // Using 3rd method
        if (checkIfVowelUsingRegex(inputVal)) {
            System.out.println("Vowel");
        } else {
            System.out.println("Consonant");
        }
        consoleInput.close();
    }

    private static boolean checkIfVowelUsingIndexOf(char c) {
        return VOWELS.indexOf(c) != -1;
    }

    private static boolean checkIfVowelUsingSwitchCase(char c) {
        switch (c) {
        case 'a':
            return true;
        case 'e':
            return true;
        case 'i':
            return true;
        case 'o':
            return true;
        case 'u':
            return true;
        case 'A':
            return true;
        case 'E':
            return true;
        case 'I':
            return true;
        case 'O':
            return true;
        case 'U':
            return true;
        default:
            return false;
        }
    }

    private static boolean checkIfVowelUsingRegex(char c) {
        return Pattern.matches(REGEX, Character.toString(c));
    }
}
