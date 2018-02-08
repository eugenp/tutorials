package com.baeldung.methodoverridingoverloading.util;

public class Multiplier {
    
    public int multiply(int a, int b) {
        return a * b;
    }
    
    public int multiply(int a, int b, int c) {
        return a * b * c;
    }
    
    public double multiply(int a, long b) {
        return a * b;
    }
    
    public double multiply(double a, double b) {
        return a * b;
    }
    
    /*
    public long multiply(int a, long b) {
        return a * b;
    }
    
    public long multiply(long a, int b) {
        return a * b;
    }
    */
}
