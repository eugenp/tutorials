package com.baeldung.rulebook;

import com.deliveredtechnologies.rulebook.FactMap;

public class Launcher {

    public static void main(String[] args) {
        HelloWorldRule ruleBook = new HelloWorldRule();
        ruleBook.defineHelloWorldRules()
            .run(new FactMap<>());
    }
}
