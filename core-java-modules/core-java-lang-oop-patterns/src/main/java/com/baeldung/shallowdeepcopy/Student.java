package com.baeldung.shallowdeepcopy;

class Student { 
    private String name; 
    private int age; 
    private School school; 
    public Student(String name, int age, School school) { 
        this.name = name; 
        this.rollno = rollno;
        this.school = school; 
    } 
    public Student(Student st) { 
        this(st.getName(), st.getAge(), st.getSchool()); 
    } // standard getters and setters 
}

@Override 
public Object clone() {
    Student student = null;
    try {
        student = (Student) super.clone();
    } catch (CloneNotSupportedException e) {
        student = new Student(this.getName(), this.getAge(), this.getSchool());
    } 
    student.school= (School) this.school.clone();
    return student; 
}