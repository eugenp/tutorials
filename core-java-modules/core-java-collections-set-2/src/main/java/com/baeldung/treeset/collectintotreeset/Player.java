package com.baeldung.treeset.collectintotreeset;

public class Player implements Comparable<Player> {
    private String name;
    private int age;
    private int numberOfPlayed;
    private int numberOfWins;

    public Player(String name, int age, int numberOfPlayed, int numberOfWins) {
        this.name = name;
        this.age = age;
        this.numberOfPlayed = numberOfPlayed;
        this.numberOfWins = numberOfWins;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getNumberOfPlayed() {
        return numberOfPlayed;
    }

    public int getNumberOfWins() {
        return numberOfWins;
    }

    @Override
    public int compareTo(Player o) {
        return Integer.compare(age, o.age);
    }
}
