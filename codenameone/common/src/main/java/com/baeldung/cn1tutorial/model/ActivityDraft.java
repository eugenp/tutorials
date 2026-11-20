package com.baeldung.cn1tutorial.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Mutable editing model used by {@code ActivityForm}.
 * <p>
 * Codename One forms often keep temporary UI state while the user moves between pickers, dialogs
 * and sub-forms. Using a dedicated draft object keeps that temporary state separate from the
 * immutable {@link Activity} persisted by the store.
 */
public class ActivityDraft {
    private String title = "";
    private ActivityCategory category = ActivityCategory.ROUTINE;
    private LocalDate date;
    private LocalTime time;
    private String notes = "";
    private boolean completed;
    private PlaceInfo place;

    /**
     * Creates an empty draft with the same defaults used by the "new activity" form.
     *
     * @return a new draft instance
     */
    public static ActivityDraft empty() {
        return new ActivityDraft();
    }

    /**
     * Copies an existing activity into a mutable draft.
     *
     * @param activity activity being edited
     * @return editable copy of the activity
     */
    public static ActivityDraft from(Activity activity) {
        ActivityDraft draft = new ActivityDraft();
        draft.title = activity.title();
        draft.category = activity.category();
        draft.date = activity.date();
        draft.time = activity.time();
        draft.notes = activity.notes();
        draft.completed = activity.completed();
        draft.place = activity.place();
        return draft;
    }

    /**
     * Converts the draft back into an immutable activity.
     *
     * @param id identifier to keep or assign
     * @return immutable activity snapshot
     */
    public Activity toActivity(String id) {
        return new Activity(
                id,
                title == null ? "" : title.trim(),
                category == null ? ActivityCategory.ROUTINE : category,
                date,
                time,
                notes == null ? "" : notes.trim(),
                completed,
                place,
                Instant.now()
        );
    }

    /**
     * @return current draft title
     */
    public String title() {
        return title;
    }

    /**
     * Updates the draft title.
     *
     * @param title new title text
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return selected category
     */
    public ActivityCategory category() {
        return category;
    }

    /**
     * Updates the selected category.
     *
     * @param category new category
     */
    public void setCategory(ActivityCategory category) {
        this.category = category;
    }

    /**
     * @return selected date, or {@code null}
     */
    public LocalDate date() {
        return date;
    }

    /**
     * Updates the selected date.
     *
     * @param date new date, or {@code null}
     */
    public void setDate(LocalDate date) {
        this.date = date;
    }

    /**
     * @return selected time, or {@code null}
     */
    public LocalTime time() {
        return time;
    }

    /**
     * Updates the selected time.
     *
     * @param time new time, or {@code null}
     */
    public void setTime(LocalTime time) {
        this.time = time;
    }

    /**
     * @return draft notes
     */
    public String notes() {
        return notes;
    }

    /**
     * Updates the notes field.
     *
     * @param notes new notes text
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return current completion flag
     */
    public boolean completed() {
        return completed;
    }

    /**
     * Updates the completion flag.
     *
     * @param completed new completion flag
     */
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    /**
     * @return currently selected place, or {@code null}
     */
    public PlaceInfo place() {
        return place;
    }

    /**
     * Updates the place currently attached to the draft.
     *
     * @param place place selection, or {@code null}
     */
    public void setPlace(PlaceInfo place) {
        this.place = place;
    }
}
