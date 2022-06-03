package com.baeldung.cloning;

import java.io.Serializable;

public class DeepCloneableMyClass implements Cloneable {

    // Primitives
    private int i;
    private char c;

    // Object References
    private Ref1 o;

    /**
     * Primary constructor
     * @param i
     * @param c
     * @param o
     */
    public DeepCloneableMyClass(int i, char c, Ref1 o) {
        this.i = i;
        this.c = c;
        this.o = o;
    }

    /**
     * Copy Constructor
     * @return
     * @throws CloneNotSupportedException
     */
    public DeepCloneableMyClass(DeepCloneableMyClass instance) {
        this.i = instance.i;
        this.c = instance.c;
        this.o = new Ref1(new Ref2());
    }

    /**
     * Overridden implementation of Object.clone()
     * @return
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException {
        DeepCloneableMyClass clonedObject = (DeepCloneableMyClass) super.clone();
        clonedObject.o = (Ref1) this.o.clone();
        return clonedObject;
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public char getC() {
        return c;
    }

    public void setC(char c) {
        this.c = c;
    }

    public Ref1 getO() {
        return o;
    }

    public void setO(Ref1 o) {
        this.o = o;
    }
}
