package com.baeldung.injectiontype;

public class HumanService {
    // Dependent Object required
    private Action action;
    private String name;

    // Constructor to inject dependency
    public HumanService(Action action) {
        this.action = action;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    // business logic
    public void performAction() {
        action.doAction();
        System.out.println("Action completed by " + name);
    }
}