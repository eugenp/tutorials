package com.baeldung.constructordi.annotation.dhaval;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("subject")
public class Subject {

    private Lesson lesson;

    @Autowired
    public Subject(Lesson lesson) {
        System.out.println("Hi, I'm Dependent class");
        this.lesson = lesson;
    }

    public void beginStudy() {
        lesson.startLearning();
    }
}
