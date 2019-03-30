package com.baeldung.keywords.finalkeyword;

public final class Child extends Parent {

    @Override
    void method1(int arg1, final int arg2) {
        // OK
    }

/*    @Override
    void method2() {
        // Compilation error
    }*/

}
