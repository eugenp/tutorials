package com.baeldung.pattern.richdomainmodel;

public class Player {
    private int points ;
    final String name;

    public Player(String name) {
        this(name, 0);
    }

    private Player(String name, int points) {
        this.name = name;
        this.points = 0;
    }

    public void gainPoint() {
        points++;
    }

    public boolean hasScoreBiggerThan(Score score) {
        return this.points > score.points();
    }

    public int pointsDifference(Player other) {
        return points - other.points;
    }

    public String name() {
        return name;
    }

    public String score() {
        return Score.from(points).label();
    }
}