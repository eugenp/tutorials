package com.baeldung.algorithms.caesarcipher;

public class CaesarCipherAlgorithm {
    private static final char LETTER_A = 'a';
    private static final int LETTERS_COUNT = 26;

    public String cipher(String message, int shift) {
        if (shift < 0 || shift >= LETTERS_COUNT) {
            throw new IllegalArgumentException("Shift must be between 0 and 25");
        }

        StringBuilder result = new StringBuilder();

        for (char character : message.toCharArray()) {
            if (character != ' ') {
                int shiftFromA = (character + shift - LETTER_A) % LETTERS_COUNT;
                result.append((char) (LETTER_A + shiftFromA));
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }
}
