package com.baeldung.cloning;

/**
 * The StudentDeepClone illustrates how can we create a copy of an object
 * by using deep cloning in java.
 * @author Bharath Ganesh
 */
public class StudentDeepClone implements Cloneable {

    private int id;
    private String name;
    private Marks marks;

    public StudentDeepClone(int id, String name,Marks marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {

        StudentDeepClone studentDeepClone = (StudentDeepClone) super.clone();
        Marks marksClone=(Marks) marks.clone();
        studentDeepClone.setMarks(marksClone);
        return studentDeepClone;
    }

    @Override
    public String toString() {
        return "id: " + getId() + ", name: " + getName();
    }


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Marks getMarks() {
        return marks;
    }

    public void setMarks(Marks marks) {
        this.marks = marks;
    }
}


