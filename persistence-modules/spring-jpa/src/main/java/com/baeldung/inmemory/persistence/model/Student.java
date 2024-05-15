package com.baeldung.inmemory.persistence.model;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Student {

    @Id
    private long id;
    private String name;

    @ElementCollection
    private List<String> tags = new ArrayList<>();

    @ElementCollection
    private List<SkillTag> skillTags = new ArrayList<>();

    @ElementCollection
    private List<KVTag> kvTags = new ArrayList<>();

    public Student() {
    }

    public Student(long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags.addAll(tags);
    }

    public List<SkillTag> getSkillTags() {
        return skillTags;
    }

    public void setSkillTags(List<SkillTag> skillTags) {
        this.skillTags.addAll(skillTags);
    }

    public List<KVTag> getKVTags() {
        return this.kvTags;
    }

    public void setKVTags(List<KVTag> kvTags) {
        this.kvTags.addAll(kvTags);
    }

}
