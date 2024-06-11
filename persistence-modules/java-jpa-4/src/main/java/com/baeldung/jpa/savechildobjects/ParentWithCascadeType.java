package com.baeldung.jpa.savechildobjects;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

@Entity
public class ParentWithCascadeType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "parent", cascade = CascadeType.PERSIST)
    private List<BidirectionalChildWithCascadeType> bidirectionalChildren;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "parent_id")
    private List<UnidirectionalChild> joinColumnUnidirectionalChildren;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<BidirectionalChildWithCascadeType> getChildren() {
        return bidirectionalChildren;
    }

    public void setChildren(List<BidirectionalChildWithCascadeType> bidirectionalChildren) {
        this.bidirectionalChildren = bidirectionalChildren;
    }

    public void addChildren(List<BidirectionalChildWithCascadeType> bidirectionalChildren) {
        this.bidirectionalChildren = bidirectionalChildren;
        this.bidirectionalChildren.forEach(c -> c.setParent(this));
    }

    public List<UnidirectionalChild> getJoinColumnUnidirectionalChildren() {
        return joinColumnUnidirectionalChildren;
    }

    public void setJoinColumnUnidirectionalChildren(List<UnidirectionalChild> joinColumnUnidirectionalChildren) {
        this.joinColumnUnidirectionalChildren = joinColumnUnidirectionalChildren;
    }
}
