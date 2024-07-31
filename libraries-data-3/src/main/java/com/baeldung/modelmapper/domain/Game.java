package com.baeldung.modelmapper.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    private Long id;
    private String name;
    private Long timestamp;

    private Player creator;
    private final List<Player> players = new ArrayList<>();

    private GameSettings settings;

    public Game() {
    }

    public Game(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTimestamp() {
        return this.timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Player getCreator() {
        return this.creator;
    }

    public void setCreator(Player creator) {
        this.creator = creator;
        addPlayer(creator);
    }

    public void addPlayer(Player player) {
        this.players.add(player);
    }

    public List<Player> getPlayers() {
        return Collections.unmodifiableList(this.players);
    }

    public GameSettings getSettings() {
        return this.settings;
    }

    public void setSettings(GameSettings settings) {
        this.settings = settings;
    }
}
