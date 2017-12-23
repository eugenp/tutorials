package com.baeldung.di.types.xml;

public class Department {

    private int departmentId;
    private Secretary secretary;

    public Department(int departmentId, Secretary secretary) {
        this.departmentId = departmentId;
        this.secretary = secretary;
    }

    public Department() {
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public void setSecretary(Secretary secretary) {
        this.secretary = secretary;
    }

    @Override
    public String toString() {
        return "Department{" +
                "departmentId=" + departmentId +
                ", secretary=" + secretary +
                '}';
    }
}
