package com.baeldung.deepcopy;

public class Student {

    private String fullName;
    private int semester;
    private UKAddress address;

    public Student() {
    }

    public Student(String fullName, int semester, UKAddress address) {
        this.fullName = fullName;
        this.semester = semester;
        this.address = address;
    }

    public static Student newInstance(Student student) {
        Student copy = new Student();
        copy.fullName = student.fullName;
        copy.semester = student.semester;
        copy.address = UKAddress.newInstance(student.address);

        return copy;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public UKAddress getAddress() {
        return address;
    }

    public void setAddress(UKAddress address) {
        this.address = address;
    }
}
