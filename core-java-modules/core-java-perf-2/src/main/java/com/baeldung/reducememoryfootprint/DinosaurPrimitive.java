package com.baeldung.reducememoryfootprint;

public class DinosaurPrimitive {

    int id;
    short age;
    String feedingHabits;
    DinosaurType type;
    String habitat;
    boolean isExtinct;
    boolean isCarnivorous;
    boolean isHerbivorous;
    boolean isOmnivorous;

    public DinosaurPrimitive(int id, short age, String feedingHabits, DinosaurType type, String habitat, boolean isExtinct, boolean isCarnivorous, boolean isHerbivorous, boolean isOmnivorous) {
        this.id = id;
        this.age = age;
        this.feedingHabits = feedingHabits;
        this.type = type;
        this.habitat = habitat;
        this.isExtinct = isExtinct;
        this.isCarnivorous = isCarnivorous;
        this.isHerbivorous = isHerbivorous;
        this.isOmnivorous = isOmnivorous;
    }
}
