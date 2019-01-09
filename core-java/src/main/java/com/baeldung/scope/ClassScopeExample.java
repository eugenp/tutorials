package org.baeldung.variable.scope.examples;

public class ClassScopeExample {

    Integer amount = 0;

    public void exampleMethod() {
        amount++;
    }

    public void anotherExampleMethod() {
        amount--;
    }

}
