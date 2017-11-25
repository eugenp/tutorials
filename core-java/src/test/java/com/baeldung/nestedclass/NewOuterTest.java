package com.baeldung.nestedclass;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class NewOuterTest {

    int a = 1;
    static int b = 2;

    public class InnerClass {
        int a = 3;
        static final int b = 4;

        @Test
        public void whenShadowing_thenCorrect() {
            assertEquals(3, a);
            assertEquals(4, b);
            assertEquals(1, NewOuterTest.this.a);
            assertEquals(2, NewOuterTest.b);
            assertEquals(2, NewOuterTest.this.b);
        }
    }

    @Test
    public void shadowingTest() {
        NewOuterTest outer = new NewOuterTest();
        NewOuterTest.InnerClass inner = outer.new InnerClass();
        inner.whenShadowing_thenCorrect();

    }
}