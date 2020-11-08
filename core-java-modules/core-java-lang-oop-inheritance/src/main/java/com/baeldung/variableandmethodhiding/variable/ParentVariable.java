package com.baeldung.variableandmethodhiding.variable;

/**
 * Created by Gebruiker on 5/7/2018.
 */
public class ParentVariable {

    String instanceVariable = "parent variable";

    public void printInstanceVariable() {
        System.out.println(instanceVariable);
    }
}
