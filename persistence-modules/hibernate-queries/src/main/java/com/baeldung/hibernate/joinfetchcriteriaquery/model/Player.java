package com.baeldung.hibernate.joinfetchcriteriaquery.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.io.Serializable;

@Entity
public class Player {

    @Id
    private int id;
    private String playerName;
    private String teamName;
    private int age;

    @ManyToOne
    @JoinColumn(name = "league_id")
    private League league;

    public Player() {
    }

    public Player(int id, String playerName, String teamName, int age, League league) {
        this.id = id;
        this.playerName = playerName;
        this.teamName = teamName;
        this.age = age;
        this.league = league;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public League getLeague() {
        return league;
    }

    public void setLeague(League league) {
        this.league = league;
    }

    @Override
    public String toString() {
        return "Player{" + "id=" + id + ", playerName='" + playerName + '\'' + ", teamName='" + teamName + '\'' + ", age=" + age + ", league_id=" + (league != null ? league.getId() : "null") + '}';
    }
}
