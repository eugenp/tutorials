package com.baeldung.methodinjections;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component("schoolNotification")
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class SchoolNotification {
    private String name;
    private int marks;

    public SchoolNotification(String name, int marks) {
        this.setName(name);
        this.setMarks(marks);
    }

    public String checkResult() {
        if (marks >= 70) {
            return this.name + ":FIRST_CLASS";
        } else if (marks < 70 && marks > 45) {
            return this.name + ":SECOND_CLASS";
        }
        return this.name + ":FAIL";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        this.marks = marks;
    }
}
