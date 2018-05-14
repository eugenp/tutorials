package com.baeldung.scope.variable;

/**
 * Created by Gebruiker on 5/6/2018.
 */
public class VariableHidingDemo {
    public static void main(String[] args) {
        HideVariable variable = new HideVariable();
        variable.printLocalVariable();
        variable.printInstanceVariable();

        ParentVariable parentVariable = new ParentVariable();
        ParentVariable childVariable = new ChildVariable();

        parentVariable.printInstanceVariable();
        childVariable.printInstanceVariable();
    }
}
