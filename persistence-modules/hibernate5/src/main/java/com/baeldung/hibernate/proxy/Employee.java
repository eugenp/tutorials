package com.baeldung.hibernate.proxy;

import org.hibernate.annotations.BatchSize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@BatchSize(size = 5)
public class Employee implements Serializable {

    @Id
    @GeneratedValue (strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workplace_id")
    private Company workplace;

    @Column(name = "first_name")
    private String firstName;

    public Employee() { }

    public Employee(Company workplace, String firstName) {
        this.workplace = workplace;
        this.firstName = firstName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Company getWorkplace() {
        return workplace;
    }

    public void setWorkplace(Company workplace) {
        this.workplace = workplace;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) &&
                Objects.equals(workplace, employee.workplace) &&
                Objects.equals(firstName, employee.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, workplace, firstName);
    }
}
