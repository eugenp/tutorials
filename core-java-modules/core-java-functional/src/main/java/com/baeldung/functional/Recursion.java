package com.baeldung.functional;

public class Recursion {

    public static Integer headRecursion(Integer number) {

        return (number == 1) ? 1 : number * headRecursion(number - 1);

    }

    public static Integer tailRecursion(Integer number, Integer result) {

        return (number == 1) ? result : tailRecursion(number - 1, result * number);

    }

}
