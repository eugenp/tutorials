package com.baeldung.hibernate.booleanconverters.model;

import java.util.UUID;

import org.hibernate.type.NumericBooleanConverter;
import org.hibernate.type.TrueFalseConverter;
import org.hibernate.type.YesNoConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Question {

    @Id
    private UUID id;
    private String content;
    @Convert(converter = YesNoConverter.class)
    private Boolean correctAnswer;
    @Convert(converter = TrueFalseConverter.class)
    private Boolean shouldBeAsked;
    @Convert(converter = NumericBooleanConverter.class)
    private Boolean isEasy;
    private Boolean wasAskedBefore;

    public Question() {
    }

    public Question(UUID id, String content, Boolean correctAnswer, Boolean shouldBeAsked, Boolean isEasy, Boolean wasAskedBefore) {
        this.id = id;
        this.content = content;
        this.correctAnswer = correctAnswer;
        this.shouldBeAsked = shouldBeAsked;
        this.isEasy = isEasy;
        this.wasAskedBefore = wasAskedBefore;
    }

    public UUID getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public Boolean getCorrectAnswer() {
        return correctAnswer;
    }

    public Boolean getShouldBeAsked() {
        return shouldBeAsked;
    }

    public Boolean isEasy() {
        return isEasy;
    }

    public Boolean getWasAskedBefore() {
        return wasAskedBefore;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setCorrectAnswer(Boolean correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public void setShouldBeAsked(Boolean shouldBeAsked) {
        this.shouldBeAsked = shouldBeAsked;
    }

    public void setEasy(Boolean easy) {
        isEasy = easy;
    }

    public void setWasAskedBefore(Boolean wasAskedBefore) {
        this.wasAskedBefore = wasAskedBefore;
    }
}
