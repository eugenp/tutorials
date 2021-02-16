package com.baeldung.hibernate.lazyCollection.model;

import javax.persistence.*;

@Entity
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long rank;

    public Employee() {

    }

    public Employee(String name, Long rank, Branch mainBranch, Branch subBranch, Branch additionalBranch) {
        this.name = name;
        this.rank = rank;
        this.mainBranch = mainBranch;
        this.subBranch = subBranch;
        this.additionalBranch = additionalBranch;
    }

    @ManyToOne
    private Branch mainBranch;

    @ManyToOne
    private Branch subBranch;

    @ManyToOne
    private Branch additionalBranch;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public Branch getMainBranch() {
        return mainBranch;
    }

    public void setMainBranch(Branch mainBranch) {
        this.mainBranch = mainBranch;
    }

    public Branch getSubBranch() {
        return subBranch;
    }

    public void setSubBranch(Branch subBranch) {
        this.subBranch = subBranch;
    }

    public Branch getAdditionalBranch() {
        return additionalBranch;
    }

    public void setAdditionalBranch(Branch additionalBranch) {
        this.additionalBranch = additionalBranch;
    }
}