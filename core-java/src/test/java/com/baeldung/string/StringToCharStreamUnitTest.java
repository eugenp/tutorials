package com.baeldung.string;

import org.junit.Test;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by smatt on 09/06/2017.
 */
public class StringToCharStreamUnitTest {

    String testString = "Tests";

    @Test
    public void givenTestString_whenChars_thenReturnIntStream() {
         assertTrue(testString.chars() instanceof IntStream);
    }

    @Test
    public void givenTestString_whenCodePoints_thenReturnIntStream() {
        assertTrue(testString.codePoints() instanceof IntStream);
    }

    @Test
    public void givenIntStream_whenMapToObj_thenReturnCharacterStream() {
        Stream<Character> characterStream = testString.chars().mapToObj(c -> (char) c);
        Stream<Character> characterStream1 = testString.codePoints().mapToObj(c -> (char) c);
        assertNotNull("IntStream returned by chars() did not map to Stream<Character>", characterStream);
        assertNotNull("IntStream returned by codePoints() did not map to Stream<Character>", characterStream1);
    }

}
