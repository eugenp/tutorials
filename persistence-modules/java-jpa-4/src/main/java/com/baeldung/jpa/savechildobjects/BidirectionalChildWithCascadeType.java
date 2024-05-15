package com.baeldung.jpa.savechildobjects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class BidirectionalChildWithCascadeType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private ParentWithCascadeType parent;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ParentWithCascadeType getParent() {
        return parent;
    }

    public void setParent(ParentWithCascadeType parent) {
        this.parent = parent;
    }
}
