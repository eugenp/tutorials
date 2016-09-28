package com.baeldung.java.regex;

public class Result {
    private boolean found = false;
    private int count = 0;

    public Result() {

    }

    public boolean isFound() {
        return found;
    }

    public void setFound(boolean found) {
        this.found = found;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

}
