package com.baeldung.copy;

public class ShallowCopyStudent implements Cloneable {
    private String name;
    private int grade;
    private Lesson lesson;

    public ShallowCopyStudent(String name, int grade, Lesson lesson) {
        setName(name);
        setGrade(grade);
        setLesson(lesson);
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Lesson getLesson() {
        return lesson;
    }

    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
