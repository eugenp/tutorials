package com.baeldung.cloning;

class EmployeeDeepClone {
    private int id;
    private Department department;

    public EmployeeDeepClone(EmployeeDeepClone employeeDeepClone) {
        this.id = employeeDeepClone.getId();
        this.department = new Department(employeeDeepClone.getDepartment()
            .getDeptId(), employeeDeepClone.getDepartment()
            .getDeptName());
    }

    // standard getters/setters/constructors/toString
    public EmployeeDeepClone() {
    }

    public EmployeeDeepClone(int id, Department department) {
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
