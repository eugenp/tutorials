package com.baeldung.skipselectbeforeinsert.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import org.springframework.data.domain.Persistable;

@Entity
public class PersistableTask implements Persistable<Integer> {

    @Id
    private int id;
    private String description;

    @Transient
    private boolean isNew = true;

    public void setNew(boolean isNew) {
        this.isNew = isNew;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
