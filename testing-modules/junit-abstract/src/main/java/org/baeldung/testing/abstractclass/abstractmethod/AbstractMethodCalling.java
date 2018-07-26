/**
 * 
 */
package org.baeldung.testing.abstractclass.abstractmethod;

/**
 * When method calls abstract method.
 */
public abstract class AbstractMethodCalling {

    public abstract String abstractFunc();

    public String defaultImpl() {
        String res = abstractFunc();
        return (res == null) ? "Default" : (res + " Default");
    }
}
