package com.baeldung;

import java.io.*;
import java.util.ArrayList;

public class DeepCopyObject implements Cloneable, Serializable {

    ArrayList<Integer> integers;

    public DeepCopyObject(DeepCopyObject object) {
        this.integers = new ArrayList<>(object.getIntegers());
    }

    public DeepCopyObject deepCopy() throws IOException, ClassNotFoundException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this);
        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (DeepCopyObject) ois.readObject();
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public DeepCopyObject(ArrayList<Integer> integers) {
        this.integers = integers;
    }

    public ArrayList<Integer> getIntegers() {
        return integers;
    }

    public void setIntegers(ArrayList<Integer> integers) {
        this.integers = integers;
    }
}
