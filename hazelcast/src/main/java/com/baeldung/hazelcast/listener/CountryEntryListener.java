package com.baeldung.hazelcast.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.MapEvent;
import com.hazelcast.map.listener.EntryAddedListener;
import com.hazelcast.map.listener.EntryEvictedListener;
import com.hazelcast.map.listener.EntryRemovedListener;
import com.hazelcast.map.listener.EntryUpdatedListener;
import com.hazelcast.map.listener.MapClearedListener;
import com.hazelcast.map.listener.MapEvictedListener;

public class CountryEntryListener implements EntryAddedListener<Long, String>, EntryRemovedListener<Long, String>, EntryUpdatedListener<Long, String>, EntryEvictedListener<Long, String>, MapEvictedListener, MapClearedListener {
    private static final Logger logger = LoggerFactory.getLogger(CountryEntryListener.class);

    public void entryAdded(EntryEvent<Long, String> event) {
        logger.info("entryAdded:" + event);
    }

    public void entryUpdated(EntryEvent<Long, String> event) {
        logger.info("entryUpdated:" + event);
    }

    public void entryRemoved(EntryEvent<Long, String> event) {
        logger.info("entryRemoved:" + event);
    }

    public void entryEvicted(EntryEvent<Long, String> event) {
        logger.info("entryEvicted:" + event);
    }

    public void mapCleared(MapEvent event) {
        logger.info("mapCleared:" + event);
    }

    public void mapEvicted(MapEvent event) {
        logger.info("mapEvicted:" + event);
    }
}
