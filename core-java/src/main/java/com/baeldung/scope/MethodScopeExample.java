package org.baeldung.variable.scope.examples;

public class MethodScopeExample {

    Integer size = 2;

    public void methodA() {
        Integer area = 2;
        area = area + size;
    }

    public void methodB() {
        size = size + 5;
    }

}
