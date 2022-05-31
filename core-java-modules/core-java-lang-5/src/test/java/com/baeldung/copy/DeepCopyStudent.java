package com.baeldung.copy;

public class DeepCopyStudent implements Cloneable {
    private String name;
    private int grade;
    private Lesson lesson;

    public DeepCopyStudent(String name, int grade, Lesson lesson) {
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
        Lesson copiedLesson = (Lesson) this.lesson.clone();
        DeepCopyStudent copiedStudent = (DeepCopyStudent) super.clone();
        //There's no need to copy of the rest attributes, since they are primitive (int) and immutable (String)
        copiedStudent.setLesson(copiedLesson);
        return copiedStudent;
    }
}