package com.baeldung.hibernate.lifecycle;

import javax.persistence.*;

@Entity
@Table(name = "Football_Player")
public class FootballPlayer {
    @Id
    @GeneratedValue
    private long id;

    @Column
    private String name;

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

    @Override
    public String toString() {
        return "FootballPlayer{" + "id=" + id + ", name='" + name + '\'' + '}';
    }
}