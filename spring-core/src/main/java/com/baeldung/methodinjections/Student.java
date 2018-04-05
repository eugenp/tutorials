package com.baeldung.methodinjections;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.stereotype.Component;

@Component("studentBean")
public class Student {

    private String id;

    /**
     * Injects a prototype bean SchoolNotification into Singleton student
     */
    @Lookup
    public SchoolNotification getNotification(String name) {
        // spring overrides and returns a SchoolNotification instance
        return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
