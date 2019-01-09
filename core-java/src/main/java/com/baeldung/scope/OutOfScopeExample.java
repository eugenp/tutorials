package org.baeldung.variable.scope.examples;

public class OutOfScopeExample {
    
    public void methodWithAVariableDeclaredInside() {
        String name = "John Doe";
        System.out.println(name);
    }
    
    public void methodWithoutVariables() {
        System.out.println("Pattrick");
    }

}
