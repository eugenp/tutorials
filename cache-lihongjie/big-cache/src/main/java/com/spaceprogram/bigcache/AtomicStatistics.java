package com.spaceprogram.bigcache;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * User: treeder
 * Date: Sep 7, 2008
 * Time: 12:31:36 PM
 */
public class AtomicStatistics implements Statistics {

    public AtomicInteger puts = new AtomicInteger();

    public AtomicInteger gets = new AtomicInteger();

    public AtomicInteger removes = new AtomicInteger();

    public AtomicInteger hits = new AtomicInteger();

    public int getGets() {
        return gets.get();
    }

    public int getHits() {
        return hits.get();
    }

    public int getMisses() {
        return gets.get() - hits.get();
    }

    public int getPuts() {
        return puts.get();
    }

    public int getRemoves() {
        return removes.get();
    }

}
