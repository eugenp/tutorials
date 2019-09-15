package com.baeldung.methodinjections;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component("studentService")
public abstract class StudentServices {

    private Map<String, SchoolNotification> notes = new HashMap<>();

    @Lookup
    protected abstract SchoolNotification getNotification(String name);

    public String appendMark(String name, Integer mark) {
        SchoolNotification notification = notes.computeIfAbsent(name, exists -> getNotification(name));
        return notification.addMark(mark);
    }
}
