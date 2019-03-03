package com.baeldung.hexagonalArchitecture.adapters.jaxb;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.baeldung.hexagonalArchitecture.domain.Player;

@XmlRootElement
public class PlayerListWrapper {
    private List<Player> players;

    public PlayerListWrapper() {
        players = new ArrayList<Player>();
    }

    @XmlElement(name = "player")
    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

}