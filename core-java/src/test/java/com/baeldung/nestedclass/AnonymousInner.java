package com.baeldung.nestedclass;

import org.junit.Test;

abstract class SimpleAbstractClass {
    abstract void run();
}

/**
 * 测试：嵌套类
 */
public class AnonymousInner {

    @Test
    public void run() {
        SimpleAbstractClass simpleAbstractClass = new SimpleAbstractClass() {
            @Override
            void run() {
                System.out.println("Running Anonymous Class...");
            }
        };
        simpleAbstractClass.run();
    }
}