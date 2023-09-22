package com.baeldung.shallowdeepcopy;

class School {
    private String schoolName;
    public School(School sc) {
       this(sc.getSchoolName());
    } 
    public School(String schoolName) { 
        this.schoolName = schoolName; 
    } 
    // standard getters and setters 
}

@Override
public Object clone() { 
    try { 
        return (School) super.clone();
    } catch (CloneNotSupportedException e) { 
        return new School(this.getSchoolName()); 
    } 
}

