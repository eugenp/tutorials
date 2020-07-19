package com.baeldung.rules.jess;

import jess.JessException;
import jess.Rete;

public class JessHello {
    public static final String RULES_FILE = "helloJess.clp";

    public static void main(String[] args) throws JessException {
        Rete engine = new Rete();
        engine.reset();

        engine.batch(RULES_FILE);

        engine.run();
    }
}
