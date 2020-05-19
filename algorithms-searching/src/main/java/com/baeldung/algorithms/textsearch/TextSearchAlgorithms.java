package com.baeldung.algorithms.textsearch;

import java.math.BigInteger;
import java.util.Random;

public class TextSearchAlgorithms {
    public static long getBiggerPrime(int m) {
        BigInteger prime = BigInteger.probablePrime(getNumberOfBits(m) + 1, new Random());
        return prime.longValue();
    }

    public static long getLowerPrime(long number) {
        BigInteger prime = BigInteger.probablePrime(getNumberOfBits(number) - 1, new Random());
        return prime.longValue();
    }

    private static int getNumberOfBits(final int number) {
        return Integer.SIZE - Integer.numberOfLeadingZeros(number);
    }

    private static int getNumberOfBits(final long number) {
        return Long.SIZE - Long.numberOfLeadingZeros(number);
    }

    public static int simpleTextSearch(char[] pattern, char[] text) {
        int patternSize = pattern.length;
        int textSize = text.length;

        int i = 0;

        while ((i + patternSize) <= textSize) {
            int j = 0;
            while (text[i + j] == pattern[j]) {
                j += 1;
                if (j >= patternSize)
                    return i;
            }
            i += 1;
        }

        return -1;
    }

    public static int RabinKarpMethod(char[] pattern, char[] text) {
        int patternSize = pattern.length; // m
        int textSize = text.length; // n

        long prime = getBiggerPrime(patternSize);

        long r = 1;
        for (int i = 0; i < patternSize - 1; i++) {
            r *= 2;
            r = r % prime;
        }

        long[] t = new long[textSize];
        t[0] = 0;

        long pfinger = 0;

        for (int j = 0; j < patternSize; j++) {
            t[0] = (2 * t[0] + text[j]) % prime;
            pfinger = (2 * pfinger + pattern[j]) % prime;
        }

        int i = 0;
        boolean passed = false;

        int diff = textSize - patternSize;
        for (i = 0; i <= diff; i++) {
            if (t[i] == pfinger) {
                passed = true;
                for (int k = 0; k < patternSize; k++) {
                    if (text[i + k] != pattern[k]) {
                        passed = false;
                        break;
                    }
                }

                if (passed) {
                    return i;
                }
            }

            if (i < diff) {
                long value = 2 * (t[i] - r * text[i]) + text[i + patternSize];
                t[i + 1] = ((value % prime) + prime) % prime;
            }
        }
        return -1;

    }

    public static int KnuthMorrisPrattSearch(char[] pattern, char[] text) {
        int patternSize = pattern.length; // m
        int textSize = text.length; // n

        int i = 0, j = 0;

        int[] shift = KnuthMorrisPrattShift(pattern);

        while ((i + patternSize) <= textSize) {
            while (text[i + j] == pattern[j]) {
                j += 1;
                if (j >= patternSize)
                    return i;
            }

            if (j > 0) {
                i += shift[j - 1];
                j = Math.max(j - shift[j - 1], 0);
            } else {
                i++;
                j = 0;
            }
        }
        return -1;
    }

    public static int[] KnuthMorrisPrattShift(char[] pattern) {
        int patternSize = pattern.length;

        int[] shift = new int[patternSize];
        shift[0] = 1;

        int i = 1, j = 0;

        while ((i + j) < patternSize) {
            if (pattern[i + j] == pattern[j]) {
                shift[i + j] = i;
                j++;
            } else {
                if (j == 0)
                    shift[i] = i + 1;

                if (j > 0) {
                    i = i + shift[j - 1];
                    j = Math.max(j - shift[j - 1], 0);
                } else {
                    i = i + 1;
                    j = 0;
                }
            }
        }
        return shift;
    }

    public static int BoyerMooreHorspoolSimpleSearch(char[] pattern, char[] text) {
        int patternSize = pattern.length;
        int textSize = text.length;

        int i = 0, j = 0;

        while ((i + patternSize) <= textSize) {
            j = patternSize - 1;
            while (text[i + j] == pattern[j]) {
                j--;
                if (j < 0)
                    return i;
            }
            i++;
        }
        return -1;
    }

    public static int BoyerMooreHorspoolSearch(char[] pattern, char[] text) {

        int shift[] = new int[256];

        for (int k = 0; k < 256; k++) {
            shift[k] = pattern.length;
        }

        for (int k = 0; k < pattern.length - 1; k++) {
            shift[pattern[k]] = pattern.length - 1 - k;
        }

        int i = 0, j = 0;

        while ((i + pattern.length) <= text.length) {
            j = pattern.length - 1;

            while (text[i + j] == pattern[j]) {
                j -= 1;
                if (j < 0)
                    return i;
            }

            i = i + shift[text[i + pattern.length - 1]];

        }
        return -1;
    }
}
