package com.baeldung.jpa.index;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(indexes = {
        @Index(columnList = "firstName"),
        @Index(name = "fn_index", columnList = "id"),
        @Index(name = "multiIndex1", columnList = "firstName, lastName"),
        @Index(name = "multiIndex2", columnList = "lastName, firstName"),
        @Index(name = "multiSortIndex", columnList = "firstName, lastName DESC"),
        @Index(name = "uniqueIndex", columnList = "firstName", unique = true),
        @Index(name = "uniqueMultiIndex", columnList = "firstName, lastName", unique = true)
})
public class Student implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return Objects.equals(id, student.id) &&
                Objects.equals(firstName, student.firstName) &&
                Objects.equals(lastName, student.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName);
    }
}
