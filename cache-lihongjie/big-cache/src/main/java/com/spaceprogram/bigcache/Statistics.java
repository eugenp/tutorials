package com.spaceprogram.bigcache;

/**
 * User: treeder
 * Date: Sep 7, 2008
 * Time: 1:37:21 PM
 */
public interface Statistics {


    int getGets();

    int getHits();

    int getMisses();

    int getPuts();

    int getRemoves();
}
