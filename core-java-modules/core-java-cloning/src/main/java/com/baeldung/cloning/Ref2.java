package com.baeldung.cloning;

import java.io.Serializable;

public class Ref2 implements Cloneable, Serializable {

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
