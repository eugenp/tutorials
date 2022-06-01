package com.baeldung.cloning;

public class CloneableMyClass implements Cloneable {

    // Primitives
    int i;
    char c;

    // Object References
    Ref1 o;

    /**
     * Default no-arg constructor
     */
    public CloneableMyClass() {}

    /**
     * Default constructor
     * @param i
     * @param c
     * @param o
     */
    public CloneableMyClass(int i, char c, Ref1 o) {
        this.i = i;
        this.c = c;
        this.o = o;
    }

    /**
     * Copy Constructor
     * @return
     * @throws CloneNotSupportedException
     */
    public CloneableMyClass(CloneableMyClass instance) {
        this.i = instance.i;
        this.c = instance.c;
        this.o = instance.o;
    }

    /**
     * Overridden implementation of Object.clone()
     * @return
     * @throws CloneNotSupportedException
     */
    public Object clone() throws CloneNotSupportedException {
        CloneableMyClass clonedObject = (CloneableMyClass) super.clone();
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
