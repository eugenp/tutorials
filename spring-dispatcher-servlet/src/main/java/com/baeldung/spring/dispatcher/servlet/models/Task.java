package com.baeldung.spring.dispatcher.servlet.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Task {
    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'hh:mm")
    private Date due;

    private Set<Attachment> attachments = new HashSet<>();

    public Task() {
    }

    public Task(Date due) {
        this.due = due;
    }

    public Task(String description, Date due) {
        this.description = description;
        this.due = due;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDue() {
        return due;
    }

    public void setDue(Date due) {
        this.due = due;
    }

    public Set<Attachment> getAttachments() {
        return attachments;
    }

    public void setAttachments(Set<Attachment> attachments) {
        this.attachments = attachments;
    }
}
