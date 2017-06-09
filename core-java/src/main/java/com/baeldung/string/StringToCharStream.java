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

             //let's use the Stream API to manipulate a string
            //this will count the occurrence of each character in the test string

            System.out.println("Counting Occurrence of Letter");
            String testString = "Noww";

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
