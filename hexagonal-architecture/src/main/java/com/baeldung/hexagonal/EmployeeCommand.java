package com.baeldung.hexagonal;

public class EmployeeCommand {
    public String entityClass;

    public String getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(String entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public String toString() {
        return "EmployeeCommand [entityClass=" + entityClass + "]";
    }

}
