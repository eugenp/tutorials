package com.baeldung.returnfirstnonnull;

class LazyEvaluate {

    String methodA() {
        return null;
    }

    String methodB() {
        return "first non null";
    }

    String methodC() {
        return "second non null";
    }
}