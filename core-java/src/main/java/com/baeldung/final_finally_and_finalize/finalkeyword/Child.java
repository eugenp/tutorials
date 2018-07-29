package com.baeldung.final_finally_and_finalize.finalkeyword;

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
