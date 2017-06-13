package com.baeldung.string;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StringToCharStream {

    private StringToCharStream() {
        String testString = "tests";

        IntStream intStream = testString.chars();
        IntStream intStream1 = testString.codePoints();

        Stream<Character> characterStream = intStream.mapToObj(c -> (char) c);
        Stream<Character> characterStream1 = intStream1.mapToObj(c -> (char) c);

        System.out.println("Counting Occurrence of Letter");

        Map<Character, Integer> map = "Noww".codePoints()
          .mapToObj(c -> (char) c)
          .filter(Character::isLetter)
          .collect(Collectors.toMap(c -> c, c -> 1, Integer::sum));

        //printing out the result here
        System.out.println(map.toString());

    }

    public static IntStream getIntStreamFromCodePoints(String test) {
        return test.codePoints();
    }

    public static IntStream getIntStreamFromChars(String test) {
        return test.chars();
    }

    public static Stream<Character> mapIntStreamToCharStream(IntStream intStream) {
        return intStream.mapToObj(c -> (char) c);
    }

    public static Stream<String> mapIntStreamToStringStream(IntStream intStream) {
        return intStream.mapToObj(c -> String.valueOf((char) c));
    }


    public static void main(String[] args) {

        new StringToCharStream();

    }
}
