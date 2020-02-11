package com.baeldung.jcabi;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.jcabi.aspects.Async;
import com.jcabi.aspects.Cacheable;
import com.jcabi.aspects.LogExceptions;
import com.jcabi.aspects.Loggable;
import com.jcabi.aspects.Quietly;
import com.jcabi.aspects.RetryOnFailure;
import com.jcabi.aspects.UnitedThrow;

public class JcabiAspectJ {

    public static void main(String[] args) {
        try {
            displayFactorial(10);
            getFactorial(10).get();

            String result = cacheExchangeRates();
            if (result != cacheExchangeRates()) {
                System.out.println(result);
            }

            divideByZero();
        } catch(Exception e) {
            e.printStackTrace();
        }

        divideByZeroQuietly();
        try {
            processFile();  
        } catch(Exception e) {
            e.printStackTrace();
        }

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
    public static String cacheExchangeRates() {
        String result = null;
        try {
            URL exchangeRateUrl = new URL("https://api.exchangeratesapi.io/latest");
            URLConnection con = exchangeRateUrl.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            result = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @LogExceptions
    public static void divideByZero() {
        int x = 1/0;
    }

    @RetryOnFailure(attempts = 2, types = {java.lang.NumberFormatException.class})
    @Quietly
    public static void divideByZeroQuietly() {
        int x = 1/0;
    }

    @UnitedThrow(IllegalStateException.class)
    public static void processFile() throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new FileReader("baeldung.txt"));
        reader.readLine();
        
        Thread thread = new Thread();
        thread.wait(2000);
    }

}
