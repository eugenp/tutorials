package com.baeldung.cloning;

import java.io.Serializable;

public class Ref1 implements Cloneable, Serializable {

    // Object References
    private Ref2 p;

    /**
     * Primary constructor
     * @param p
     */
    public Ref1(Ref2 p) {
        this.p = p;
    }

    /**
     * Overridden implementation of Object.clone()
     * @return
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException {
        Ref1 clonedObject = (Ref1) super.clone();
        clonedObject.p = (Ref2) this.p.clone();
        return clonedObject;
    }

    public Ref2 getP() {
        return p;
    }

    public void setP(Ref2 p) {
        this.p = p;
    }
}
