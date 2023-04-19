package com.baeldung.cloning;

/**
 *  The StudentShallowClone illustrates how can we create a copy of an object
 *  by using shallow cloning in java.
 *  @author Bharath Ganesh
 */
// To make a clone of StudentShallowClone, it should implement Cloneable interface, which is a marker interface.
// Invoking Object's clone method on an instance that does not implement the Cloneable interface results in the
// exception CloneNotSupportedException being thrown.
public class StudentShallowClone implements Cloneable {

    private int id;
    private String name;
    private Marks marks;

    public StudentShallowClone(int id, String name, Marks marks) {
        this.id = id;
        this.name = name;
        this.marks = marks;
    }

    // Classes that implements Cloneable interface should override Object.clone (which is protected)
    // with a public method.
    @Override
    public Object clone() throws CloneNotSupportedException {
        //invoke the clone method of the object class
        return super.clone();
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

    public Marks getMarks() {
        return marks;
    }

    public void setMarks(Marks marks) {
        this.marks = marks;
    }

    public void setName(String name) {
        this.name = name;
    }
}

