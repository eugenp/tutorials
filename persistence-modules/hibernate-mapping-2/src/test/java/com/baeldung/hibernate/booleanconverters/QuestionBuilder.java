package com.baeldung.hibernate.booleanconverters;

import java.util.UUID;

import com.baeldung.hibernate.booleanconverters.model.Question;

public class QuestionBuilder {
    private UUID id;
    private String content;
    private Boolean correctAnswer;
    private Boolean shouldBeAsked;
    private Boolean isEasy;
    private Boolean wasAskedBefore;

    public QuestionBuilder id(UUID id) {
        this.id = id;
        return this;
    }

    public QuestionBuilder content(String content) {
        this.content = content;
        return this;
    }

    public QuestionBuilder correctAnswer(Boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
        return this;
    }

    public QuestionBuilder shouldBeAsked(Boolean shouldBeAsked) {
        this.shouldBeAsked = shouldBeAsked;
        return this;
    }

    public QuestionBuilder isEasy(Boolean isEasy) {
        this.isEasy = isEasy;
        return this;
    }

    public QuestionBuilder wasAskedBefore(Boolean wasAskedBefore) {
        this.wasAskedBefore = wasAskedBefore;
        return this;
    }

    public Question build() {
        return new Question(id, content, correctAnswer, shouldBeAsked, isEasy, wasAskedBefore);
    }
}