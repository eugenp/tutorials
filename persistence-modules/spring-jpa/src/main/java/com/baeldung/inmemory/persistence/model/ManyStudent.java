package com.baeldung.inmemory.persistence.model;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ManyStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String name;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "manystudent_manytags", joinColumns = @JoinColumn(name = "manystudent_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "manytag_id", referencedColumnName = "id"))
    private Set<ManyTag> manyTags = new HashSet<>();

    public ManyStudent() {
    }

    public ManyStudent(String name) {
        this.name = name;
    }

    public Set<ManyTag> getManyTags() {
        return manyTags;
    }

    public void setManyTags(Set<ManyTag> manyTags) {
        this.manyTags.addAll(manyTags);
    }
}
