package com.baeldung.copy;

public class Lesson implements Cloneable {
    private String name;
    private int credit;

    public Lesson(String name, int credit) {
        setName(name);
        setCredit(credit);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
