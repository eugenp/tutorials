package com.baeldung.hibernate.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BaseballPlayer {

    @Id
    private Long playerId; // Long instead of long
    private String name;

    public BaseballPlayer(String name) {
        this.name = name;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}