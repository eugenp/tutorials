package com.baeldung.string;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by smatt on 26/05/2017.
 */
public class StringToCharStream {

    public StringToCharStream() {

        System.out.println("Before Mapping");

        String testString = "String";
        //this will return IntStream Object
        testString.chars()
               .forEach(System.out::println); //83, 116, 114 . . .

       System.out.println();
       System.out.println("After Mapping");
       testString.codePoints()
                .mapToObj(c -> (char) c) //this is where integers are converted to char type
                .forEach(c -> System.out.println(c));



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
