package com.baeldung.beaninjectiontypes.constructor;

public class Library {

    private Manager manager;

    public Library(Manager manager) {
        this.manager = manager;
    }

    public String getManagerName() {
        return manager.getName();
    }
}
