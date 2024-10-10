package com.baeldung.arrayscollections.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@Table(name = "migrating_users")
public class MigratingUser {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @JdbcTypeCode(SqlTypes.VARBINARY)
    private List<String> tags;

    private List<String> newTags;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getNewTags() {
        return newTags;
    }

    public void setNewTags(List<String> newTags) {
        this.newTags = newTags;
    }
}
