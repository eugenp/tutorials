package com.baeldung.persistence.deletion.model;

import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "FOO")
@Where(clause = "DELETED = 0")
public class Foo {

    public Foo() {
        super();
    }

    public Foo(final String name) {
        super();
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DELETED")
    private Integer deleted = 0;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "BAR_ID")
    private Bar bar;

    public Bar getBar() {
        return bar;
    }

    public void setBar(final Bar bar) {
        this.bar = bar;
    }

    public long getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setDeleted() {
        this.deleted = 1;
    }
}
