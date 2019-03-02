package com.baeldung.hexagonalArchitecture.domain;

public class Player {
    private String name;

    private long score;

    public String getName() {
        return name;
    }

    public Player(String name, long score) {
        super();
        this.name = name;
        this.score = score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

}
