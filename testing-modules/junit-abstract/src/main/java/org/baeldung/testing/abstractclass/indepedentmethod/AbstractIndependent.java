package org.baeldung.testing.abstractclass.indepedentmethod;

/**
 * Test Independent Method
 * 
 */
public abstract class AbstractIndependent {

    public abstract int abstractFunc();

    public String defaultImpl() {
        return "DEFAULT-1";
    }
}
