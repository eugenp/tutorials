package com.baeldung.annotations.componentscanautoconfigure.healthcare;

public class Doctor {

    @Override
    public String toString() {
        return "Doctor" + this.hashCode();
    }
}
