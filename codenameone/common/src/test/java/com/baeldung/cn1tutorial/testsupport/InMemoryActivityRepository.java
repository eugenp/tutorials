package com.baeldung.cn1tutorial.testsupport;

import com.baeldung.cn1tutorial.model.Activity;
import com.baeldung.cn1tutorial.service.ActivityRepository;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory repository used by tests that do not want file-system persistence.
 */
public class InMemoryActivityRepository extends ActivityRepository {
    private final List<Activity> storedActivities = new ArrayList<Activity>();
    private IOException loadException;

    /**
     * Returns a defensive copy of the in-memory activities, or throws the configured exception.
     */
    @Override
    public List<Activity> loadActivities() throws IOException {
        if (loadException != null) {
            throw loadException;
        }
        return new ArrayList<Activity>(storedActivities);
    }

    /**
     * Replaces the in-memory data set.
     */
    @Override
    public void saveActivities(List<Activity> activities) {
        storedActivities.clear();
        storedActivities.addAll(activities);
    }

    /**
     * Clears the stored data and any configured load failure.
     */
    @Override
    public void reset() {
        storedActivities.clear();
        loadException = null;
    }

    /**
     * Seeds the repository with a known data set.
     *
     * @param activities activities to store
     */
    public void seed(List<Activity> activities) {
        storedActivities.clear();
        storedActivities.addAll(activities);
    }

    /**
     * @return defensive copy of the current in-memory data set
     */
    public List<Activity> snapshot() {
        return new ArrayList<Activity>(storedActivities);
    }

    /**
     * Configures an exception to throw during the next load.
     *
     * @param loadException exception to throw
     */
    public void setLoadException(IOException loadException) {
        this.loadException = loadException;
    }
}
