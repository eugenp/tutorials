package com.baeldung.java;

/**
 * Plain java object with three java variables and with default values assigned.
 */
public class SimpleDemoClass {
    public String name = "Baeldung";
    public int integer = 2;
    public boolean flag = true;

    public SimpleDemoClass() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInteger() {
        return integer;
    }

    public void setInteger(int integer) {
        this.integer = integer;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
