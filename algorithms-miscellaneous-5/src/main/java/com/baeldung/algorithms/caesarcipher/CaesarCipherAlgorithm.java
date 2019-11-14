package com.baeldung.algorithms.caesarcipher;

public class CaesarCipherAlgorithm {
    private static final char LETTER_A = 'a';
    private static final int LETTERS_COUNT = 26;

    public String cipher(String message, int offset) {
        StringBuilder result = new StringBuilder();

        for (char character : message.toCharArray()) {
            if (character != ' ') {
                int originalAlphabetPosition = character - LETTER_A;
                int newAlphabetPosition = (originalAlphabetPosition + offset) % LETTERS_COUNT;
                char newCharacter = (char) (LETTER_A + newAlphabetPosition);
                result.append(newCharacter);
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }

    public String decipher(String message, int offset) {
        return cipher(message, LETTERS_COUNT - (offset % LETTERS_COUNT));
    }
}
