package com.baeldung.algorithms.caesarcipher;

import java.util.stream.IntStream;

public class CaesarCipherAlgorithm {
    private static final char LETTER_A = 'a';
    private static final int LETTERS_COUNT = 26;
    private static final double[] ENGLISH_LETTERS_FREQUENCY = {0.073, 0.009, 0.030, 0.044, 0.130, 0.028, 0.016, 0.035, 0.074, 0.002, 0.003, 0.035, 0.025, 0.078, 0.074, 0.027, 0.003, 0.077, 0.063, 0.093, 0.027, 0.013, 0.016, 0.005, 0.019, 0.001};

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

    public int breakCipher(String message) {
        int probableOffset = -1;
        double currentSmallestChiSquare = 0;

        for (int offsetGuess = 0; offsetGuess < LETTERS_COUNT; offsetGuess++) {
            double[] messageLettersFrequency = computeLettersFrequency(decipher(message, offsetGuess));

            if (probableOffset == -1) {
                probableOffset = offsetGuess;
                currentSmallestChiSquare = computeChiSquares(messageLettersFrequency);
            } else if (computeChiSquares(messageLettersFrequency) < currentSmallestChiSquare) {
                probableOffset = offsetGuess;
                currentSmallestChiSquare = computeChiSquares(messageLettersFrequency);
            }
        }

        return probableOffset;
    }

    private double computeChiSquares(double[] messageLettersFrequency) {
        return IntStream.range(0, messageLettersFrequency.length)
          .mapToDouble(letter -> messageLettersFrequency[letter] / ENGLISH_LETTERS_FREQUENCY[letter])
          .map(result -> Math.pow(result, 2))
          .sum();
    }

    private double[] computeLettersFrequency(String message) {
        double[] result = new double[LETTERS_COUNT];

        for (int letterPosition = 0; letterPosition < result.length; letterPosition++) {
            char letter = (char) (LETTER_A + letterPosition);
            long count = countLetters(letter, message);
            result[letterPosition] = ((double) count / (double) message.length());
        }

        return result;
    }

    private long countLetters(char letter, String message) {
        return message.chars()
          .filter(character -> character == letter)
          .count();
    }
}
