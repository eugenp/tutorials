package com.baeldung.reducememoryfootprint;

public class DinousaurBitPacking {

    static final short IS_EXTINCT = 0, IS_CARNIVOROUS = 1, IS_HERBIVOROUS = 2, IS_OMNIVOROUS = 3;
    int id;
    short age;
    String feedingHabits;
    String habitat;
    short flag;
    String kingdom;
    String phylum;
    String clazz;
    String order;
    String family;
    String genus;
    String species;

    public DinousaurBitPacking(int id, short age, String feedingHabits, String habitat, short flag, String kingdom, String phylum, String clazz, String order, String family, String genus, String species) {
        this.id = id;
        this.age = age;
        this.feedingHabits = feedingHabits;
        this.habitat = habitat;
        this.flag = flag;
        this.kingdom = kingdom;
        this.phylum = phylum;
        this.clazz = clazz;
        this.order = order;
        this.family = family;
        this.genus = genus;
        this.species = species;
    }

    public static short convertToShort(boolean isExtinct, boolean isCarnivorous, boolean isHerbivorous, boolean isOmnivorous) {
        short result = 0;
        result |= (short) (isExtinct ? 1 << IS_EXTINCT : 0);
        result |= (short) (isCarnivorous ? 1 << IS_CARNIVOROUS : 0);
        result |= (short) (isHerbivorous ? 1 << IS_HERBIVOROUS : 0);
        result |= (short) (isOmnivorous ? 1 << IS_OMNIVOROUS : 0);
        return result;
    }

    public static boolean convertToBoolean(short value, short flagPosition) {
        return (value >> flagPosition & 1) == 1;
    }
}
