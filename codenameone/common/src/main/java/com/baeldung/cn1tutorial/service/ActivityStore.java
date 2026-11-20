package com.baeldung.cn1tutorial.service;

import com.baeldung.cn1tutorial.model.Activity;
import com.baeldung.cn1tutorial.util.IdGenerator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * In-memory store sitting between forms and the repository.
 * <p>
 * The store gives the UI a simple mutable view of the current data set, while persistence remains
 * delegated to {@link ActivityRepository}.
 */
public class ActivityStore {
    private final ActivityRepository repository;
    private final List<Activity> activities = new ArrayList<Activity>();

    /**
     * @param repository repository used to load and persist activities
     */
    public ActivityStore(ActivityRepository repository) {
        this.repository = repository;
    }

    /**
     * Reloads the in-memory list from the repository.
     */
    public void load() {
        try {
            activities.clear();
            activities.addAll(repository.loadActivities());
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to load activities", ex);
        }
    }

    /**
     * Returns a display snapshot in the same order in which activities were inserted.
     * <p>
     * The repository stores the list in this same order, so the home screen remains predictable
     * without adding extra sorting rules to the tutorial app. The live list is not exposed directly
     * so forms cannot accidentally mutate it.
     *
     * @return insertion-order copy of the current activities
     */
    public List<Activity> getActivitiesInInsertionOrder() {
        return new ArrayList<Activity>(activities);
    }

    /**
     * Finds one activity by identifier.
     *
     * @param id identifier to locate
     * @return matching activity, or {@code null}
     */
    public Activity findById(String id) {
        for (Activity activity : activities) {
            if (activity.id().equals(id)) {
                return activity;
            }
        }
        return null;
    }

    /**
     * Inserts or replaces an activity and persists the full store.
     *
     * @param activity activity to save
     * @return saved activity, possibly with a generated id
     */
    public Activity save(Activity activity) {
        int existingIndex = indexOf(activity.id());
        Activity toSave = activity.id() == null || activity.id().trim().length() == 0
                ? activity.withCompleted(activity.completed())
                : activity;
        if (existingIndex >= 0) {
            activities.set(existingIndex, toSave);
        } else {
            String id = toSave.id();
            if (id == null || id.trim().length() == 0) {
                toSave = new Activity(
                        IdGenerator.newId(),
                        toSave.title(),
                        toSave.category(),
                        toSave.date(),
                        toSave.time(),
                        toSave.notes(),
                        toSave.completed(),
                        toSave.place(),
                        toSave.updatedAt()
                );
            }
            activities.add(toSave);
        }
        persist();
        return toSave;
    }

    /**
     * Deletes one activity by id.
     *
     * @param id activity id
     */
    public void delete(String id) {
        int index = indexOf(id);
        if (index >= 0) {
            activities.remove(index);
            persist();
        }
    }

    /**
     * Flips the completion state of one activity.
     *
     * @param id activity id
     * @return updated activity, or {@code null} if not found
     */
    public Activity toggleCompleted(String id) {
        Activity activity = findById(id);
        if (activity == null) {
            return null;
        }
        Activity updated = activity.withCompleted(!activity.completed());
        save(updated);
        return updated;
    }

    /**
     * @return number of activities currently loaded
     */
    public int count() {
        return activities.size();
    }

    /**
     * Counts activities marked as completed.
     *
     * @return completed activity count
     */
    public int completedCount() {
        int count = 0;
        for (Activity activity : activities) {
            if (activity.completed()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Clears both memory and persisted storage.
     */
    public void reset() {
        activities.clear();
        repository.reset();
    }

    /**
     * Returns the position of one activity inside the live list.
     *
     * @param id activity id
     * @return index, or {@code -1}
     */
    private int indexOf(String id) {
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i).id().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Persists the current in-memory state.
     */
    private void persist() {
        try {
            repository.saveActivities(activities);
        } catch (IOException ex) {
            throw new IllegalStateException("Unable to save activities", ex);
        }
    }
}
