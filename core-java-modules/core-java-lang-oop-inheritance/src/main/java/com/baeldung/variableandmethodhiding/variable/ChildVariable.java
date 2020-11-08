package com.baeldung.variableandmethodhiding.variable;

/**
 * Created by Gebruiker on 5/7/2018.
 */
public class ChildVariable extends ParentVariable {

    String instanceVariable = "child variable";

    public void printInstanceVariable() {
        System.out.println(instanceVariable);
    }
}
