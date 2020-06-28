package com.baeldung.privatemodifier;

public class Employee {

    private String privateId;
    private String name;
    private boolean manager;

    public Employee(String id, String name) {
        setPrivateId(id);
        setName(name);
    }

    private Employee(String id, String name, boolean managerAttribute) {
        this.privateId = id;
        this.name = name;
        this.privateId = id + "_ID-MANAGER";
    }

    public void setPrivateId(String customId) {
        if (customId.endsWith("_ID")) {
            this.privateId = customId;
        } else {
            this.privateId = customId + "_ID";
        }
    }

    public String getPrivateId() {
        return privateId;
    }

    public boolean isManager() {
        return manager;
    }

    public void elevateToManager() {
        if ("Carl".equals(this.name)) {
            setManager(true);
        }
    }

    private void setManager(boolean manager) {
        this.manager = manager;
    }

    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public static Employee buildManager(String id, String name) {
        return new Employee(id, name, true);
    }
    
}
