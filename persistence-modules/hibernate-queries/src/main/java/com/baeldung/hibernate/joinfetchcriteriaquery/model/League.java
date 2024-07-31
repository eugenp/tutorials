package com.baeldung.hibernate.joinfetchcriteriaquery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
public class League {

    @Id
    private int id;
    private String name;

    @OneToMany(mappedBy = "league")
    private List<Player> players = new ArrayList<>();

    public League() {
    }

    public League(int id, String name) {
        this.id = id;
        this.name = name;
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

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "League{" + "id=" + id + ", name='" + name + '\'' + ", players=" + players.size() + '}';
    }
}
