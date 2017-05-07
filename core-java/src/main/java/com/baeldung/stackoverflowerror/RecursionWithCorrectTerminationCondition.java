package com.baeldung.stackoverflowerror;

public class RecursionWithCorrectTerminationCondition {
    public static int calculateFactorial(final int number) {
        return number <= 1 ? 1 : number * calculateFactorial(number-1);
     }

     public static void main(String[] args) {
         final int numberToCalcFactorial= -1;
         System.out.println(calculateFactorial(numberToCalcFactorial));
         
     }
}
