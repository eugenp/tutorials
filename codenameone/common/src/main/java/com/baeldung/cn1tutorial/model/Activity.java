package com.baeldung.cn1tutorial.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Immutable domain object representing one routine entry shown and edited by the app.
 * <p>
 * The UI never mutates this record directly. Forms edit an {@link ActivityDraft}, then the
 * draft is converted back into a new {@code Activity} instance. This keeps persistence and UI
 * refresh logic predictable, which is especially convenient in Codename One where forms are often
 * rebuilt from current state.
 */
public record Activity(
        String id,
        String title,
        ActivityCategory category,
        LocalDate date,
        LocalTime time,
        String notes,
        boolean completed,
        PlaceInfo place,
        Instant updatedAt
) {
    /**
     * Returns whether the activity currently carries an explicit date.
     *
     * @return {@code true} when {@link #date()} is not {@code null}
     */
    public boolean hasDate() {
        return date != null;
    }

    /**
     * Returns whether the activity currently carries an explicit time.
     *
     * @return {@code true} when {@link #time()} is not {@code null}
     */
    public boolean hasTime() {
        return time != null;
    }

    /**
     * Returns whether the activity is associated with a place.
     *
     * @return {@code true} when {@link #place()} is not {@code null}
     */
    public boolean hasPlace() {
        return place != null;
    }

    /**
     * Returns whether the notes field contains meaningful text.
     *
     * @return {@code true} when notes are not blank
     */
    public boolean hasNotes() {
        return notes != null && notes.trim().length() > 0;
    }

    /**
     * Creates a copy with a different completion flag and a refreshed timestamp.
     * <p>
     * This is used by the store so completion toggles remain immutable updates.
     *
     * @param value new completion state
     * @return a new activity instance
     */
    public Activity withCompleted(boolean value) {
        return new Activity(id, title, category, date, time, notes, value, place, Instant.now());
    }
}
