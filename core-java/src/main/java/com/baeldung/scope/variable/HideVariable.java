package com.baeldung.scope.variable;

/**
 * Created by Gebruiker on 5/6/2018.
 */
public class HideVariable {

    private String instanceVariable = "this is instance variable";

    HideVariable() {
        instanceVariable = "constructor local variable";
        System.out.println(instanceVariable);
    }

    public void printLocalVariable() {
        instanceVariable = "method local variable";
        System.out.println(instanceVariable);
    }

    public void printInstanceVariable() {
        System.out.println(this.instanceVariable);
    }
}
