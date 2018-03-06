package com.spaceprogram.bigcache;

/**
 * User: treeder
 * Date: Sep 22, 2008
 * Time: 9:00:19 AM
 */
public class Times {

    private String name;

    private int size;

    private long totalOut;

    private long totalIn;

    public Times(String name) {

        this.name = name;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void addOut(long duration) {
        totalOut += duration;
    }

    public void addIn(long duration) {
        totalIn += duration;
    }

    public String toString() {
        return name + ": out=" + totalOut + ", in=" + totalIn + ", size=" + size;
    }
}
