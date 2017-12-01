package com.baeldung.nestedclass;

import org.junit.Test;

public class NewOuter {

    int a = 1;
    static int b = 2;

    public class InnerClass {
        int a = 3;
        static final int b = 4;

        public void run() {
            System.out.println("a = " + a);
            System.out.println("b = " + b);
            System.out.println("NewOuterTest.this.a = " + NewOuter.this.a);
            System.out.println("NewOuterTest.b = " + NewOuter.b);
            System.out.println("NewOuterTest.this.b = " + NewOuter.this.b);
        }
    }

    @Test
    public void test() {
        NewOuter outer = new NewOuter();
        NewOuter.InnerClass inner = outer.new InnerClass();
        inner.run();

    }
}