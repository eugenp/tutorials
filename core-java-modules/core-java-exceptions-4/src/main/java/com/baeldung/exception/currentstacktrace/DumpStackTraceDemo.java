package com.baeldung.exception.currentstacktrace;

public class DumpStackTraceDemo 
{ 
    public static void main(String[] args) {
        methodA(); 
    } 

    public static void methodA() {
        try {
            int num1 = 5/0; // java.lang.ArithmeticException: divide by zero
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
