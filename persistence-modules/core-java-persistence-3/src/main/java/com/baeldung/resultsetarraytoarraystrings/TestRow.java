package com.baeldung.resultsetarraytoarraystrings;

import java.util.Arrays;

public class TestRow {
    private int id;
    private String[] testArray;

    // Constructors, getters, and setters
    public TestRow() {}

    public TestRow(int id, String[] testArray) {
        this.id = id;
        this.testArray = testArray;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String[] getTestArray() {
        return testArray;
    }

    public void setTestArray(String[] testArray) {
        this.testArray = testArray;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestRow testRow = (TestRow) o;
        return id == testRow.id &&
            Arrays.equals(testArray, testRow.testArray);
    }
}
