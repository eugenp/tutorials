package com.baeldung.architecturehexagonal.domain.model;

import java.util.Objects;

public class Reservation {
    private final Long id;
    private final String customerName;
    private final Integer persons;
    private final Table table;

    public Reservation(Long id, String customerName, Integer persons, Table table) {
        this.id = id;
        this.customerName = customerName;
        this.persons = persons;
        this.table = table;
    }

    public static Reservation of(Long id, String customerName, Integer persons, Table table) {
        return new Reservation(id, customerName, persons, table);
    }

    public Long getId() {
        return id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public Integer getPersons() {
        return persons;
    }

    public Table getTable() {
        return table;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Reservation that = (Reservation) o;
        return getId().equals(that.getId()) && getCustomerName().equals(that.getCustomerName()) && getPersons().equals(that.getPersons()) && getTable().equals(that.getTable());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getCustomerName(), getPersons(), getTable());
    }
}
