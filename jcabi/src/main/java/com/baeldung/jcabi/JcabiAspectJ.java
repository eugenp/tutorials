package com.baeldung.jcabi;


import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.jcabi.aspects.Async;
import com.jcabi.aspects.Cacheable;
import com.jcabi.aspects.LogExceptions;
import com.jcabi.aspects.Loggable;
import com.jcabi.aspects.Parallel;
import com.jcabi.aspects.Quietly;
import com.jcabi.aspects.RetryOnFailure;
import com.jcabi.aspects.Timeable;
import com.jcabi.aspects.UnitedThrow;

public class JcabiAspectJ {

    public static void main(String[] args) {
        try {
            displayFactorial(10);
            getFactorial(10).get();
            
            Double number = cacheRandomNumber();
            if (number != cacheRandomNumber()) {
                System.out.println(number);
            }
            
            divideByZero();
            
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        divideByZeroQuietly();
        parallelExecution();
        
    }

    @Loggable
    @Async
    public static void displayFactorial(int number) {
        long result = factorial(number);
        System.out.println(result);
    }
    
    @Loggable
    @Async
    public static Future<Long> getFactorial(int number) {
        Future<Long> factorialFuture = CompletableFuture.supplyAsync(() -> factorial(number));
        return factorialFuture;
    }
    
    /**
     * Finds factorial of a number
     * @param number
     * @return
     */
    public static long factorial(int number) {
        long result = 1; 
        for(int i=number;i>0;i--) {
            result *= i; 
        } 
        return result; 
    }

    @Loggable
    @Cacheable(lifetime = 2, unit = TimeUnit.SECONDS)
    public static Double cacheRandomNumber() {
        return Math.random();
    }
    
    @UnitedThrow(IllegalStateException.class)
    @LogExceptions
    public static void divideByZero() {
        int x = 1/0;
    }
    
    @RetryOnFailure(attempts = 2, types = {java.lang.NumberFormatException.class})
    @Quietly
    public static void divideByZeroQuietly() {
        int x = 1/0;
    }
    
    @Parallel(threads = 4)
    public static void parallelExecution() {
        System.out.println("Calling Parallel...");
    }

}
