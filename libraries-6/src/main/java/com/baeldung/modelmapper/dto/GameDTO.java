package com.baeldung.modelmapper.dto;

import com.baeldung.modelmapper.domain.GameMode;
import java.util.List;

public class GameDTO {

    private Long id;
    private String name;
    private Long creationTime;

    private String creatorId;
    private String creator;

    private int totalPlayers;
    private List<PlayerDTO> players;

    private GameMode mode;
    private int maxPlayers;

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

    public Long getCreationTime() {
        return this.creationTime;
    }

    public void setCreationTime(Long creationTime) {
        this.creationTime = creationTime;
    }

    public String getCreatorId() {
        return this.creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public String getCreator() {
        return this.creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getTotalPlayers() {
        return this.totalPlayers;
    }

    public void setTotalPlayers(int totalPlayers) {
        this.totalPlayers = totalPlayers;
    }

    public GameMode getMode() {
        return this.mode;
    }

    public void setMode(GameMode mode) {
        this.mode = mode;
    }

    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public List<PlayerDTO> getPlayers() {
        return this.players;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }
}
