package com.baeldung.parameterpassing;

public class NonPrimitives {
    public static void main(String[] args) {
        FooClass a = new FooClass(1);
        FooClass b = new FooClass(1);
 
        System.out.printf("Before Modification: a = %d and b = %d ", a.num, b.num);
        modify(a, b);
        System.out.printf("\nAfter Modification: a = %d and b = %d ", a.num, b.num);
    }
 
    public static void modify(FooClass a1, FooClass b1) {
        a1.num++;
       
        b1 = new FooClass(1);
        b1.num++;
    }
}

class FooClass {
    public int num;
   
    public FooClass(int num) {
        this.num = num;
    }
}