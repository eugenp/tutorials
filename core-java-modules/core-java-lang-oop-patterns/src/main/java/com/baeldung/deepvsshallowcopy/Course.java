package com.baeldung.deepvsshallowcopy;

public class Course {
    private String title;
    private int hours;
    private String department;
    private String campus;

    public Course(String title, int hours, String department, String campus) {
        this.title = title;
        this.hours = hours;
        this.department = department;
        this.campus = campus;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Course(Course course) {
        this(course.getTitle(), course.getHours(), course.getCampus(), course.getDepartment());
    }

}
