package org.baeldung.variable.scope.examples;

import java.util.Arrays;
import java.util.List;

public class LoopScopeExample {

    List<String> listOfNames = Arrays.asList("Joe", "Susan", "Pattrick");

    public void iterationOfNames() {
        String allNames = "";
        for (String name : listOfNames) {
            allNames = allNames + " " + name;
        }
        // compiler error, name cannot be resolved to a variable
        // String lastNameUsed = name;
    }
}
