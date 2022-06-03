package com.baeldung.cloning;

public class Ref2 implements Cloneable {

    /**
     * Overridden implementation of Object.clone()
     * @return
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException {
        Ref2 clonedObject = (Ref2) super.clone();
        return clonedObject;
    }

 }
