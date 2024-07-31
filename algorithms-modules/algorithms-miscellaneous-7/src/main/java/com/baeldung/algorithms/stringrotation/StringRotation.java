package com.baeldung.algorithms.stringrotation;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StringRotation {

    public static boolean doubledOriginContainsRotation(String origin, String rotation) {
        if (origin.length() == rotation.length()) {
            return origin.concat(origin)
              .contains(rotation);
        }

        return false;
    }

    public static boolean isRotationUsingCommonStartWithOrigin(String origin, String rotation) {

        if (origin.length() == rotation.length()) {

            List<Integer> indexes = IntStream.range(0, origin.length())
              .filter(i -> rotation.charAt(i) == origin.charAt(0))
              .boxed()
              .collect(Collectors.toList());

            for (int startingAt : indexes) {
                if (isRotation(startingAt, rotation, origin)) {
                    return true;
                }
            }
        }

        return false;
    }

    static boolean isRotation(int startingAt, String rotation, String origin) {

        for (int i = 0; i < origin.length(); i++) {
            if (rotation.charAt((startingAt + i) % origin.length()) != origin.charAt(i)) {
                return false;
            }
        }

        return true;
    }

    public static boolean isRotationUsingQueue(String origin, String rotation) {

        if (origin.length() == rotation.length()) {
            return checkWithQueue(origin, rotation);
        }

        return false;
    }

    static boolean checkWithQueue(String origin, String rotation) {

        if (origin.length() == rotation.length()) {

            Queue<Character> originQueue = getCharactersQueue(origin);

            Queue<Character> rotationQueue = getCharactersQueue(rotation);

            int k = rotation.length();
            while (k > 0 && null != rotationQueue.peek()) {
                k--;
                char ch = rotationQueue.peek();
                rotationQueue.remove();
                rotationQueue.add(ch);
                if (rotationQueue.equals(originQueue)) {
                    return true;
                }
            }
        }

        return false;
    }

    static Queue<Character> getCharactersQueue(String origin) {
        return origin.chars()
          .mapToObj(c -> (char) c)
          .collect(Collectors.toCollection(LinkedList::new));
    }

    public static boolean isRotationUsingSuffixAndPrefix(String origin, String rotation) {

        if (origin.length() == rotation.length()) {
            return checkPrefixAndSuffix(origin, rotation);
        }

        return false;
    }

    static boolean checkPrefixAndSuffix(String origin, String rotation) {
        if (origin.length() == rotation.length()) {

            for (int i = 0; i < origin.length(); i++) {
                if (origin.charAt(i) == rotation.charAt(0)) {
                    if (checkRotationPrefixWithOriginSuffix(origin, rotation, i)) {
                        if (checkOriginPrefixWithRotationSuffix(origin, rotation, i))
                            return true;
                    }
                }
            }

        }

        return false;
    }

    private static boolean checkRotationPrefixWithOriginSuffix(String origin, String rotation, int i) {
        return origin.substring(i)
          .equals(rotation.substring(0, origin.length() - i));
    }

    private static boolean checkOriginPrefixWithRotationSuffix(String origin, String rotation, int i) {
        return origin.substring(0, i)
          .equals(rotation.substring(origin.length() - i));
    }
}
