package com.baeldung.jsonignore.nullfields;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Employee {

    private String lastName;
    private String firstName;
    private long id;

    public Employee() {
    }

    public Employee(final long id, final String firstName, final String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public long getId() {
        return id;
    }

    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }

    public void setId(final long id) {
        this.id = id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Employee employee = (Employee) o;

        if (id != employee.id) {
            return false;
        }
        if (!Objects.equals(lastName, employee.lastName)) {
            return false;
        }
        return Objects.equals(firstName, employee.firstName);
    }

    @Override
    public int hashCode() {
        int result = lastName != null ? lastName.hashCode() : 0;
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (int) (id ^ (id >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "User{" +
               "lastName='" + lastName + '\'' +
               ", firstName='" + firstName + '\'' +
               ", id=" + id +
               '}';
    }
}
