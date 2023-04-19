package com.baeldung.cloning;

/**
 * The Marks class stores the marks of a student on 100
 * for various exams. ( English, Maths, and Hindi)
 * @author Bharath Ganesh
 */
public class Marks implements Cloneable {

    private int english;
    private int maths;
    private int hindi;

    public Marks(int english, int maths, int hindi) {
        this.english = english;
        this.maths = maths;
        this.hindi = hindi;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int getEnglish() {
        return english;
    }

    public void setEnglish(int english) {
        this.english = english;
    }

    public int getMaths() {
        return maths;
    }

    public void setMaths(int maths) {
        this.maths = maths;
    }

    public int getHindi() {
        return hindi;
    }

    public void setHindi(int hindi) {
        this.hindi = hindi;
    }
}
