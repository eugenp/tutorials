package com.baeldung.algorithms.caesarcipher;

import org.apache.commons.math3.stat.inference.ChiSquareTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.IntStream;

public class CaesarCipher {

    private final Logger log = LoggerFactory.getLogger(CaesarCipher.class);

    private static final char LETTER_A = 'a';
    private static final char LETTER_Z = 'z';
    private static final int ALPHABET_SIZE = LETTER_Z - LETTER_A + 1;
    private static final double[] ENGLISH_LETTERS_PROBABILITIES = {0.073, 0.009, 0.030, 0.044, 0.130, 0.028, 0.016, 0.035, 0.074, 0.002, 0.003, 0.035, 0.025, 0.078, 0.074, 0.027, 0.003, 0.077, 0.063, 0.093, 0.027, 0.013, 0.016, 0.005, 0.019, 0.001};

    public String cipher(String message, int offset) {
        StringBuilder result = new StringBuilder();

        for (char character : message.toCharArray()) {
            if (character != ' ') {
                int originalAlphabetPosition = character - LETTER_A;
                int newAlphabetPosition = (originalAlphabetPosition + offset) % ALPHABET_SIZE;
                char newCharacter = (char) (LETTER_A + newAlphabetPosition);
                result.append(newCharacter);
            } else {
                result.append(character);
            }
        }

        return result.toString();
    }

    public String decipher(String message, int offset) {
        return cipher(message, ALPHABET_SIZE - (offset % ALPHABET_SIZE));
    }

    public int breakCipher(String message) {
        return probableOffset(chiSquares(message));
    }

    private double[] chiSquares(String message) {
        double[] expectedLettersFrequencies = expectedLettersFrequencies(message.length());

        double[] chiSquares = new double[ALPHABET_SIZE];

        for (int offset = 0; offset < chiSquares.length; offset++) {
            String decipheredMessage = decipher(message, offset);
            long[] lettersFrequencies = observedLettersFrequencies(decipheredMessage);
            double chiSquare = new ChiSquareTest().chiSquare(expectedLettersFrequencies, lettersFrequencies);
            chiSquares[offset] = chiSquare;
        }

        return chiSquares;
    }

    private long[] observedLettersFrequencies(String message) {
        return IntStream.rangeClosed(LETTER_A, LETTER_Z)
          .mapToLong(letter -> countLetter((char) letter, message))
          .toArray();
    }

    private long countLetter(char letter, String message) {
        return message.chars()
          .filter(character -> character == letter)
          .count();
    }

    private double[] expectedLettersFrequencies(int messageLength) {
        return Arrays.stream(ENGLISH_LETTERS_PROBABILITIES)
          .map(probability -> probability * messageLength)
          .toArray();
    }

    private int probableOffset(double[] chiSquares) {
        int probableOffset = 0;
        for (int offset = 0; offset < chiSquares.length; offset++) {
            log.debug(String.format("Chi-Square for offset %d: %.2f", offset, chiSquares[offset]));
            if (chiSquares[offset] < chiSquares[probableOffset]) {
                probableOffset = offset;
            }
        }

        return probableOffset;
    }
}
