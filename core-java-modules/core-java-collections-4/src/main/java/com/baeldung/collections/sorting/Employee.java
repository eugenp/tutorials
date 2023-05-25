package com.baeldung.collections.sorting;

import java.util.Date;

import javax.annotation.Nonnull;

public class Employee implements Comparable<Employee>{

    @Nonnull
    private String name;
    private Date joiningDate;

    public Employee(String name, Date joiningDate) {
        this.name = name;
        this.joiningDate = joiningDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Date joiningDate) {
        this.joiningDate = joiningDate;
    }

    @Override
    public boolean equals(Object obj) {
        return ((Employee) obj).getName()
            .equals(getName());
    }

    @Override
    public String toString() {
        return new StringBuffer().append("(")
            .append(getName())
            .append(",")
            .append(getJoiningDate())
            .append(")")
            .toString();
    }

    @Override
    public int compareTo(Employee employee) {
        return getJoiningDate().compareTo(employee.getJoiningDate());
    }
}
