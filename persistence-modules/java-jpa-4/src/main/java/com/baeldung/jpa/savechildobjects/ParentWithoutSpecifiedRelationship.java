package com.baeldung.jpa.savechildobjects;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ParentWithoutSpecifiedRelationship {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private List<BidirectionalChild> bidirectionalChildren;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BidirectionalChild> getChildren() {
        return bidirectionalChildren;
    }

    public void setChildren(List<BidirectionalChild> bidirectionalChildren) {
        this.bidirectionalChildren = bidirectionalChildren;
    }
}
