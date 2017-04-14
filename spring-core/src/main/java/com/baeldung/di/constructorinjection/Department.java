package com.baeldung.di.constructorinjection;

/**
 * Class to represent department to which the student belongs to.
 *
 */
public class Department {

    private String name;//name of the department

    public Department(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuffer sBuf = new StringBuffer();
        sBuf.append(" Department : ")
            .append(name);
        return sBuf.toString();
    }

}
