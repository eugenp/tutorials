package com.baeldung.findlargestnuminstring;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FindLargestNumInStringUnitTest {
    String inputString = "The numbers are 10, 20, and 5";
    int expectedLargestNumber = 20;

    @Test
    public void givenInputString_whenUsingBasicApproach_thenFindingLargestNumber() {
        String[] numbers = inputString.split("[^0-9]+");

        int largestNumber = Integer.MIN_VALUE;
        for (String number : numbers) {
            if (!number.isEmpty()) {
                int currentNumber = Integer.parseInt(number);
                if (currentNumber > largestNumber) {
                    largestNumber = currentNumber;
                }
            }
        }
        assertEquals(expectedLargestNumber, largestNumber);

    }

    @Test
    public void givenInputString_whenUsingRegularExpression_thenFindingLargestNumber() {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(inputString);

        int largestNumber = Integer.MIN_VALUE;
        while (matcher.find()) {
            int currentNumber = Integer.parseInt(matcher.group());
            if (currentNumber > largestNumber) {
                largestNumber = currentNumber;
            }
        }
        assertEquals(expectedLargestNumber, largestNumber);

    }

    @Test
    public void givenInputString_whenUsingStreamAndLambdaExpression_thenFindingLargestNumber() {
        int largestNumber = Arrays.stream(inputString.split("[^0-9]+"))
                .filter(s -> !s.isEmpty())
                .mapToInt(Integer::parseInt)
                .max()
                .orElse(Integer.MIN_VALUE);

        assertEquals(expectedLargestNumber, largestNumber);

    }
}