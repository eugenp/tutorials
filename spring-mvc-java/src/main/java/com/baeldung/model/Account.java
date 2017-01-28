package com.baeldung.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Account {

    private long id;
    private Employee employee;

    public Account() {
        super();
    }

    public Account(final long id, Employee employee) {
        this.id = id;
        this.setEmployee(employee);
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Account [id=" + id + ", Employee name=" + employee.getName()+ "]";
    }

}
