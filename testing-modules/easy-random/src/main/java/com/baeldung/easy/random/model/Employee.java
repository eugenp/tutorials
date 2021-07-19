package com.baeldung.easy.random.model;

import java.util.*;

public class Employee {

    private long id;
    private String firstName;
    private String lastName;
    private Department department;
    private Collection<Employee> coworkers;
    private Map<YearQuarter, Grade> quarterGrades;

    public Employee(long id, String firstName, String lastName, Department department) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.department = department;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Employee employee = (Employee) o;
        return id == employee.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public long getId() {
        return id;
    }

    public Department getDepartment() {
        return department;
    }

    public Collection<Employee> getCoworkers() {
        return Collections.unmodifiableCollection(coworkers);
    }

    public Map<YearQuarter, Grade> getQuarterGrades() {
        return Collections.unmodifiableMap(quarterGrades);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Employee.class.getSimpleName() + "[", "]").add("id=" + id)
            .add("firstName='" + firstName + "'")
            .add("lastName='" + lastName + "'")
            .add("department=" + department)
            .add("coworkers size=" + ((coworkers == null) ? 0 : coworkers.size()))
            .add("quarterGrades=" + quarterGrades)
            .toString();
    }
}
