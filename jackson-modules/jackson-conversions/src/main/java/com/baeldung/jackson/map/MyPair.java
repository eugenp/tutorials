package com.baeldung.jackson.map;

import com.fasterxml.jackson.annotation.JsonValue;

public class MyPair {

    private String first;
    private String second;

    public MyPair(String first, String second) {
        this.first = first;
        this.second = second;
    }

    public MyPair(String both) {
        String[] pairs = both.split("and");
        this.first = pairs[0].trim();
        this.second = pairs[1].trim();
    }

    @Override
    @JsonValue
    public String toString() {
        return first + " and " + second;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((first == null) ? 0 : first.hashCode());
        result = prime * result + ((second == null) ? 0 : second.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof MyPair)) {
            return false;
        }
        MyPair other = (MyPair) obj;
        if (first == null) {
            if (other.first != null) {
                return false;
            }
        } else if (!first.equals(other.first)) {
            return false;
        }
        if (second == null) {
            if (other.second != null) {
                return false;
            }
        } else if (!second.equals(other.second)) {
            return false;
        }
        return true;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getSecond() {
        return second;
    }

    public void setSecond(String second) {
        this.second = second;
    }
}