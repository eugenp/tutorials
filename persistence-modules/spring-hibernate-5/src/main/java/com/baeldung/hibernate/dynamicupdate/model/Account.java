package com.baeldung.hibernate.dynamicupdate.model;

import java.text.MessageFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class Account {

    @Id
    private int id;

    @Column
    private String name;

    @Column
    private String type;

    @Column
    private boolean active;

    public Account() {
    }

    public Account(int id, String name, String type, boolean active) {
        super();
        this.id = id;
        this.name = name;
        this.type = type;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + id;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Account other = (Account) obj;
        if (id != other.id)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return MessageFormat.format("id:{0}, name:{1}, active:{2}, type:{3}", id, name, active, type);
    }

}
