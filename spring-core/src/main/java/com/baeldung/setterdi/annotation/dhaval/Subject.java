package com.baeldung.setterdi.annotation.dhaval;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Subject {

    private Lesson lesson;

    @Autowired
    public void setLesson(Lesson lesson) {
        this.lesson = lesson;
    }

    public void beginStudy() {
        lesson.startLearning();
    }
}
