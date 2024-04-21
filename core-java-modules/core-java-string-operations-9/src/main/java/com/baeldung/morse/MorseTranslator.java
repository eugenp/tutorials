package com.baeldung.morse;

import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

public class MorseTranslator {

    private static final BidiMap<String, String> morseAlphabet = new DualHashBidiMap<>();

    static {
        morseAlphabet.put("A", ".-");
        morseAlphabet.put("B", "-...");
        morseAlphabet.put("C", "-.-.");
        morseAlphabet.put("D", "-..");
        morseAlphabet.put("E", ".");
        morseAlphabet.put("F", "..-.");
        morseAlphabet.put("G", "--.");
        morseAlphabet.put("H", "....");
        morseAlphabet.put("I", "..");
        morseAlphabet.put("J", ".---");
        morseAlphabet.put("K", "-.-");
        morseAlphabet.put("L", ".-..");
        morseAlphabet.put("M", "--");
        morseAlphabet.put("N", "-.");
        morseAlphabet.put("O", "---");
        morseAlphabet.put("P", ".--.");
        morseAlphabet.put("Q", "--.-");
        morseAlphabet.put("R", ".-.");
        morseAlphabet.put("S", "...");
        morseAlphabet.put("T", "-");
        morseAlphabet.put("U", "..-");
        morseAlphabet.put("V", "...-");
        morseAlphabet.put("W", ".--");
        morseAlphabet.put("X", "-..-");
        morseAlphabet.put("Y", "-.--");
        morseAlphabet.put("Z", "--..");
        morseAlphabet.put("0", "-----");
        morseAlphabet.put("1", ".----");
        morseAlphabet.put("2", "..---");
        morseAlphabet.put("3", "...--");
        morseAlphabet.put("4", "....-");
        morseAlphabet.put("5", ".....");
        morseAlphabet.put("6", "-....");
        morseAlphabet.put("7", "--...");
        morseAlphabet.put("8", "---..");
        morseAlphabet.put("9", "----.");
        morseAlphabet.put(".", ".-.-.-");
        morseAlphabet.put(",", "--..--");
        morseAlphabet.put("?", "..--..");
        morseAlphabet.put("'", ".----.");
        morseAlphabet.put("!", "-.-.-----.");
        morseAlphabet.put("/", "-..-.");
        morseAlphabet.put("(", "-.--.");
        morseAlphabet.put(")", "-.--.-");
        morseAlphabet.put("&", ".-...");
        morseAlphabet.put(":", "---...");
        morseAlphabet.put(";", "-.-.-.");
        morseAlphabet.put("=", "-...-");
        morseAlphabet.put("+", ".-.-.");
        morseAlphabet.put("-", "-....-");
        morseAlphabet.put("_", "..--.-");
        morseAlphabet.put("\"", ".-..-.");
        morseAlphabet.put("$", "...-..-");
        morseAlphabet.put("@", ".--.-.");
        morseAlphabet.put(" ", "/");
    }

    static String englishToMorse(String english) {
        if (english == null) {
            return null;
        }
        String upperCaseEnglish = english.toUpperCase();
        String[] morse = new String[upperCaseEnglish.length()];
        for (int index = 0; index < upperCaseEnglish.length(); index++) {
            String morseCharacter = morseAlphabet.get(String.valueOf(upperCaseEnglish.charAt(index)));
            if (morseCharacter == null) {
                throw new IllegalArgumentException("Character " + upperCaseEnglish.charAt(index) + " can't be translated to morse");
            }
            morse[index] = morseCharacter;
        }
        return String.join(" ", morse);
    }

    static String morseToEnglish(String morse) {
        if (morse == null) {
            return null;
        }
        if (morse.isEmpty()) {
            return "";
        }
        String[] morseUnitCharacters = morse.split(" ");
        StringBuilder stringBuilder = new StringBuilder();
        for (int index = 0; index < morseUnitCharacters.length; index ++) {
            String englishCharacter = morseAlphabet.getKey(morseUnitCharacters[index]);
            if (englishCharacter == null) {
                throw new IllegalArgumentException("Character " + morseUnitCharacters[index] + " is not a valid morse character");
            }
            stringBuilder.append(englishCharacter);
        }
        return stringBuilder.toString();
    }

}