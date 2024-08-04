package com.baeldung.resultsetarraytoarraystrings;

import java.util.Arrays;

public class NestedTestRow {
    private int id;
    private String[][] nestedArray;

    // Constructors, getters, and setters
    public NestedTestRow() {}

    public NestedTestRow(int id, String[][] nestedArray) {
        this.id = id;
        this.nestedArray = nestedArray;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[][] getNestedArray() {
        return nestedArray;
    }

    public void setNestedArray(String[][] nestedArray) {
        this.nestedArray = nestedArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NestedTestRow that = (NestedTestRow) o;
        return id == that.id &&
            Arrays.deepEquals(nestedArray, that.nestedArray);
    }
}