package com.baeldung.junit5.nested;

public enum Membership {
    FREE(0), SILVER(10), GOLD(20);

    private final int level;

    Membership(int level) {
        this.level = level;
    }

    public int compare(Membership other) {
        return level - other.level;
    }

}