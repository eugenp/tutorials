package org.baeldung.spring.persistence.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Parent implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "parent", cascade = CascadeType.ALL)
    private Child child;

    public Parent() {
        super();
    }

    public Parent(final Child child) {
        super();

        this.child = child;
    }

    // API

    public long getId() {
        return id;
    }

    public void setId(final long id) {
        this.id = id;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(final Child child) {
        this.child = child;
    }

}
