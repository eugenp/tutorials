package com.baeldung.methodinjections;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("studentService")
public abstract class StudentServices {

    private final Map<String, SchoolNotification> notes = new HashMap<>();

    @Lookup
    protected abstract SchoolNotification getNotification(String name);

    public String appendMark(String name, Integer mark) {
        SchoolNotification notification = notes.computeIfAbsent(name, exists -> getNotification(name));
        return notification.addMark(mark);
    }
}
