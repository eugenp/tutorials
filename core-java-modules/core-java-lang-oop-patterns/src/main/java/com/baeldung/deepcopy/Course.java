package com.baeldung.deepcopy;

public class Course {

    private String title;
    private String description;
    private Student[] activeParticipants;

    public Course() {
    }

    public Course(String title, String description, Student[] activeParticipants) {
        this.title = title;
        this.description = description;
        this.activeParticipants = activeParticipants;
    }

    public static Course newInstance(Course course) {
        Course copy = new Course();
        copy.title = course.title;
        copy.description = course.description;
        copy.activeParticipants = copyArray(course.activeParticipants);

        return copy;
    }

    public static Student[] copyArray(Student[] activeParticipants) {
        Student[] copy = new Student[activeParticipants.length];
        for (int i = 0; i < activeParticipants.length; i++) {
            copy[i] = Student.newInstance(activeParticipants[i]);
        }

        return copy;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Student[] getActiveParticipants() {
        return activeParticipants;
    }

    public void setActiveParticipants(Student[] activeParticipants) {
        this.activeParticipants = activeParticipants;
    }
}
