package com.baeldung.reducememoryfootprint;

public class Dinosaur {

    Integer id;
    Integer age;
    String feedingHabits;
    DinosaurType type;
    String habitat;
    Boolean isExtinct;
    Boolean isCarnivorous;
    Boolean isHerbivorous;
    Boolean isOmnivorous;

    public Dinosaur(Integer id, Integer age, String feedingHabits, DinosaurType type, String habitat, Boolean isExtinct, Boolean isCarnivorous, Boolean isHerbivorous, Boolean isOmnivorous) {
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
