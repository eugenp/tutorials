package com.baeldung.nestedclass;

import org.junit.Test;

/**
 * 内部类测试
 */
public class Outer {

    public class Inner {

        public void run() {
            System.out.println("Calling test...");
        }
    }

    @Test
    public void test() {
        Outer outer = new Outer();
        Outer.Inner inner = outer.new Inner();
        inner.run();
    }
}
