package com.baeldung.constructordi.dhaval;

public class Subject {
    private Lesson lesson;

    public Subject(Lesson lesson) {
        System.out.println("Hi, I'm Dependent class");
        this.lesson = lesson;
    }

    public void beginStudy() {
        lesson.startLearning();
    }
}
