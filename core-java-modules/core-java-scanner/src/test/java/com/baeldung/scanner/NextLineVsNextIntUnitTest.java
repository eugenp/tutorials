package com.baeldung.scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.InputMismatchException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class NextLineVsNextIntUnitTest {

    @Test
    void whenInputLineIsNumber_thenNextLineAndNextIntBothWork() {
        String input = "42\n";

        //nextLine()
        Scanner sc1 = new Scanner(input);
        int num1 = Integer.parseInt(sc1.nextLine());
        assertEquals(42, num1);

        //nextInt()
        Scanner sc2 = new Scanner(input);
        int num2 = sc2.nextInt();
        assertEquals(42, num2);

    }

    @Test
    void whenInputIsNotValidNumber_thenNextLineAndNextIntThrowDifferentException() {
        String input = "Nan\n";

        //nextLine() -> NumberFormatException
        Scanner sc1 = new Scanner(input);
        assertThrows(NumberFormatException.class, () -> Integer.parseInt(sc1.nextLine()));

        //nextInt() -> InputMismatchException
        Scanner sc2 = new Scanner(input);
        assertThrows(InputMismatchException.class, sc2::nextInt);
    }

    @Test
    void whenUsingNextInt_thenTheNextTokenAfterItFailsToParseIsNotConsumed() {
        String input = "42 is a magic number\n";

        //nextInt() to read '42'
        Scanner sc2 = new Scanner(input);
        int num2 = sc2.nextInt();
        assertEquals(42, num2);

        // call nextInt() again on "is"
        assertThrows(InputMismatchException.class, sc2::nextInt);

        String theNextToken = sc2.next();
        assertEquals("is", theNextToken);

        theNextToken = sc2.next();
        assertEquals("a", theNextToken);
    }

    @Test
    void whenReadingTwoInputLines_thenNextLineAndNextIntBehaveDifferently() {

        String input = new StringBuilder().append("42\n")
          .append("It is a magic number.\n")
          .toString();

        //nextLine()
        Scanner sc1 = new Scanner(input);
        int num1 = Integer.parseInt(sc1.nextLine());
        String nextLineText1 = sc1.nextLine();
        assertEquals(42, num1);
        assertEquals("It is a magic number.", nextLineText1);

        //nextInt()
        Scanner sc2 = new Scanner(input);
        int num2 = sc2.nextInt();
        assertEquals(42, num2);

        // nextInt() leaves the newline character (\n) behind
        String nextLineText2 = sc2.nextLine();
        assertEquals("", nextLineText2);
    }

}