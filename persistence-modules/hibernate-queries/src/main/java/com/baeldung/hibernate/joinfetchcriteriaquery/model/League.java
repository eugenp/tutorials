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
    private String league;

    @OneToMany(mappedBy = "league")
    private List<Player> players = new ArrayList<>();

    public League() {
    }

    public League(int id, String league) {
        this.id = id;
        this.league = league;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    @Override
    public String toString() {
        return "League{" + "id=" + id + ", league='" + league + '\'' + ", players=" + players.size() + '}';
    }
}
