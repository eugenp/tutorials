package com.baeldung.string;

import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertTrue;

/**
 * Created by smatt on 26/05/2017.
 */
public class StringToCharStream {

    public StringToCharStream() {

            String testString = "test";

            //this will return instance of IntStream
            assertTrue(testString.chars() instanceof IntStream);

            assertTrue(testString.codePoints() instanceof IntStream);

            //mapping to Stream<Character>
            Stream<Character> characterStream
                    = testString.chars().mapToObj(c -> (char) c);

            Stream<Character> characterStream2
                    = testString.codePoints().mapToObj(c -> (char) c);


            System.out.println("Counting Occurrence of Letter");
            testString = "Noww";

            //and we want to apply this by counting occurrence of each character in a string
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

            System.out.println(map.toString());
    }


    public static void main(String [] args) {
        new StringToCharStream();
    }


}
