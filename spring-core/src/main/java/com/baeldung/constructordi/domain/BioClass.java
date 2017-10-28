package com.baeldung.constructordi.domain;

/**
 * @author Baeldung
 * 
 * BIOClass bean for constrcutor DI.
 *
 */
public class BioClass {

    private int numberOfStudent;
    private int numberOfTeacher;

    public BioClass(int numberOfStudent, int numberOfTeacher) {
        this.numberOfStudent = numberOfStudent;
        this.numberOfTeacher = numberOfTeacher;
    }

    public void giveBioClassInfo() {
        System.out.println("Bio Class Info with costrcutor DI");
        System.out.println("No. of Student: " + numberOfStudent + " No. of teachers: " + numberOfTeacher);
    }
}
