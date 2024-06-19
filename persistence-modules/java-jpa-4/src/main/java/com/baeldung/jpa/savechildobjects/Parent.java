package com.baeldung.jpa.savechildobjects;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class Parent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "parent")
    private List<BidirectionalChild> bidirectionalChildren;

    @OneToMany
    @JoinColumn(name = "parent_id")
    private List<UnidirectionalChild> joinColumnUnidirectionalChildren;
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

    public List<UnidirectionalChild> getJoinColumnUnidirectionalChildren() {
        return joinColumnUnidirectionalChildren;
    }

    public void setJoinColumnUnidirectionalChildren(List<UnidirectionalChild> joinColumnUnidirectionalChildren) {
        this.joinColumnUnidirectionalChildren = joinColumnUnidirectionalChildren;
    }
}
