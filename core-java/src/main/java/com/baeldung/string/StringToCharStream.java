package com.baeldung.string;

import org.junit.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

/**
 * Created by smatt on 26/05/2017.
 */
public class StringToCharStream {

    public StringToCharStream() {

        String testString = "String";

        //this will return instance of IntStream
        Assert.assertTrue(testString.chars() instanceof IntStream);


        //now let's map the IntStream to Stream<Character>
        testString.codePoints()
                .mapToObj(c -> (char) c)
                .forEach(c -> Assert.assertTrue(c instanceof Character));



        System.out.println("Counting Occurrence of Letter");
        testString = "Noww121";

        //and we want to apply this by counting occurrence of each character in a string
        //we don't want to use foreach, so . . .
        Map<Character, Integer> map = new HashMap<>();
        testString.chars()
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
