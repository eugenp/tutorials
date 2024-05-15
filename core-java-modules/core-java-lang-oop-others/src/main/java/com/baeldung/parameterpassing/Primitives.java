package com.baeldung.parameterpassing;

public class Primitives {
    public static void main(String[] args) {
        int x = 1;
        int y = 2;
       
        System.out.printf("Before Modification: x = %d and y = %d ", x, y);
        modify(x, y);
        System.out.printf("\nAfter Modification: x = %d and y = %d ", x, y);
    }
   
    public static void modify(int x1, int y1) {
        x1 = 5;
        y1 = 10;
    }
}