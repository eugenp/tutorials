package com.baeldung.exception.nullmessage;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class Team {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

class Player {

    private static Logger LOG = LoggerFactory.getLogger(Player.class);
    private String name;
    private Team team;

    public Player(String name) {
        this.name = name;
    }

    public void output() {
        try {
            if (team != null) {
                LOG.info("Player: {}, Team: {}", name.toUpperCase(), team.getName().toUpperCase());
            } else {
                throw new IllegalArgumentException("Play's team is null");
            }
        } catch (Exception e) {
            LOG.error("Error occurred." + e.getMessage());
        }
    }

    public void outputWithStackTrace() {
        try {
            if (team != null) {
                LOG.info("Player: {}, Team: {}", name.toUpperCase(), team.getName().toUpperCase());
            } else {
                throw new IllegalArgumentException("Play's team is null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void outputWithStackTraceLog() {
        try {
            if (team != null) {
                LOG.info("Player: {}, Team: {}", name.toUpperCase(), team.getName().toUpperCase());
            } else {
                throw new IllegalArgumentException("Play's team is null");
            }
        } catch (Exception e) {
            LOG.error("Error occurred.", e);
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

}

public class ExceptionGetMessageNullUnitTest {

    @Test
    void whenCallingPlayerOutput_thenGetExceptionWithNullMessage() {
        Player kai = new Player("Kai");
        kai.setTeam(new Team());

        kai.output();
    }

    @Test
    void whenCallingPlayerOutputWithStackTrace_thenGetStackTrace() {
        Player kai = new Player("Kai");
        kai.setTeam(new Team());

        kai.outputWithStackTrace();
    }

    @Test
    void whenCallingPlayerOutputWithStackTraceLog_thenGetStackTrace() {
        Player kai = new Player("Kai");
        kai.setTeam(new Team());

        kai.outputWithStackTraceLog();
    }
}
