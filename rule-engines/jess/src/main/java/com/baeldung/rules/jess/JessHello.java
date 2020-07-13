package com.baeldung.rules.jess;

import jess.JessException;
import jess.Rete;

public class JessHello {
    public static final String RULES_FILE = "helloJess.clp";
    public static void main(String[] args) {
        Rete engine = new Rete();
        try {
            engine.reset();

            engine.batch(RULES_FILE);

            engine.run();
        } catch (JessException e) {
            e.printStackTrace();
        }
    }
}
