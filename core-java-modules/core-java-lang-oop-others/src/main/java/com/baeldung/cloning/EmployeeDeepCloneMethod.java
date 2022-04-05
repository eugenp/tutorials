package com.baeldung.cloning;

class EmployeeDeepCloneMethod implements Cloneable{
    private int id;
    private Department department;

    @Override
    protected Object clone() throws CloneNotSupportedException {
        EmployeeDeepCloneMethod clonedObj = (EmployeeDeepCloneMethod) super.clone();
        clonedObj.setDepartment((Department)this.department.clone());
        return clonedObj;
    }

    // standard getters/setters/constructors/toString
    public EmployeeDeepCloneMethod() {
    }

    public EmployeeDeepCloneMethod(int id, Department department) {
        this.id = id;
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", department=" + department + '}';
    }
}
