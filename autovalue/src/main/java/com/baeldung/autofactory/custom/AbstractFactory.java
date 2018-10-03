package com.baeldung.autofactory.custom;

/**
 * @author aiet
 */
public abstract class AbstractFactory {

    abstract CustomPhone newInstance(String brand);

}
