package com.baeldung.persistence.deletion.model;

import javax.persistence.*;

@Entity
@Table(name = "BAZ")
public class Baz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    public Baz() {
        super();
    }

    public Baz(final String name) {
        super();
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Bar [name=").append(name).append("]");
        return builder.toString();
    }
}
