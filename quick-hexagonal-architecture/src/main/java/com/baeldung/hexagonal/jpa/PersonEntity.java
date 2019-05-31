package com.baeldung.hexagonal.jpa;

import com.baeldung.hexagonal.domain.Person;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class PersonEntity implements Person {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String first;

    @Column(nullable = false)
    private String last;

    PersonEntity(String first, String last) {
        this.first = Objects.requireNonNull(first);
        this.last = Objects.requireNonNull(last);
    }

    /** for JPA */
    protected PersonEntity() {
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getFirst() {
        return first;
    }

    @Override
    public String getLast() {
        return last;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PersonEntity)) {
            return false;
        }
        PersonEntity that = (PersonEntity) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "PersonEntity{" + "id=" + id + ", first='" + first + '\'' + ", last='" + last + '\'' + '}';
    }
}
