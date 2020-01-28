package com.baeldung.jcabi_aspectj;

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
import com.jcabi.aspects.UnitedThrow;

//@Loggable
public class Example {

    public static void main(String[] args) {
        try {
            tryAsync();
            asyncFactorial(10).get();
            System.out.println("calling 2...");
            System.out.println(called2());    
            //called3();
            called4();
            System.out.println(called2());
            System.out.println(called2());
            called6();
            //called7();
            
            
        } catch(Exception e) {

        }
        called5();
        called8();
    }

    @Async
    public static void tryAsync() {
        long result = factorial(10);
        System.out.println(result);
    }
    
    @Async
    public static Future<Long> asyncFactorial(int number) {
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

    @Cacheable(lifetime = 5, unit = TimeUnit.SECONDS)
    public static Double called2() {
        return Math.random();
    }

    @LogExceptions
    public static void called3() {
        System.out.println(1/0);
    }

    @Loggable
    public static void called4() {
        System.out.println("checking loggable");
    }

    @Quietly
    public static void called5() {
        int x = 1/0;
    }

    @Parallel(threads = 4)
    public static void called6() {
        System.out.println("called6");
    }
    
    @RetryOnFailure
    //@Quietly
    public static void called7() {
        System.out.println("called7...");
        int y = 1/0;
    }
    
    @UnitedThrow //(IllegalStateException.class)
    public static void called8() {
        System.out.println("called8...");
        int y = 1/0;
    }

}
