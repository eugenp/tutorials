package org.baeldung.persistence.service;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.CascadeType;
import javax.persistence.JoinColumn;
import javax.persistence.Entity;
import javax.persistence.Column;

/**
 * The persistent class for the BAR database table.
 * 
 */
@Entity
@NamedQuery(name = "Bar.findAll", query = "SELECT b FROM Bar b")
public class Bar implements Serializable {
    private static final long serialVersionUID = 1L;

    public Bar() {
    	super();
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String name;
    
    @OneToMany(mappedBy = "bar", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @OrderBy("name ASC")
    List<Foo> fooList;

    public List<Foo> getFooList() {
        return fooList;
    }

    public void setFooList(final List<Foo> fooList) {
        this.fooList = fooList;
    }

    public int getId() {
        return this.id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

}