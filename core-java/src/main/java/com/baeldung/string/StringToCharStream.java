package com.baeldung.string;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by smatt on 26/05/2017.
 */
public class StringToCharStream {

    public StringToCharStream() {

            String testString = "test";

            //this will return instance of IntStream
            IntStream intStream = testString.chars();

            //this one as well
            IntStream intStream1 = testString.codePoints();

            //mapping to Stream<Character>
            Stream<Character> characterStream
                    = testString.chars().mapToObj(c -> (char) c);

            Stream<Character> characterStream2
                    = testString.codePoints().mapToObj(c -> (char) c);

            //let's use the Stream API to manipulate a string
            //this will count the occurrence of each character in the test string

            System.out.println("Counting Occurrence of Letter");
            testString = "Noww";

            //we don't want to use foreach, so . . .

            Map<Character, Integer> map = new HashMap<>();

            testString.codePoints()
              .mapToObj(c -> (char) c)
              .filter(c -> Character.isLetter(c))
              .forEach(c -> {
                if(map.containsKey(c)) {
                  map.put(c, map.get(c) + 1);
                } else {
                  map.put(c, 1);
                }
              });

            //printing out the result here
            System.out.println(map.toString());
    }


    public static void main(String [] args) {
        new StringToCharStream();
    }


}
