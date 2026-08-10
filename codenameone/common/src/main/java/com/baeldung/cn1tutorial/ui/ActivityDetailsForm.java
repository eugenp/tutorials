package com.baeldung.cn1tutorial.ui;

import com.baeldung.cn1tutorial.model.Activity;
import com.baeldung.cn1tutorial.model.PlaceInfo;
import com.baeldung.cn1tutorial.service.AppContext;
import com.baeldung.cn1tutorial.util.DateTimeUtil;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

/**
 * Read-only activity view.
 * <p>
 * Textual details stay scrollable in the center, while the map preview and action buttons remain
 * fixed at the bottom so they keep a predictable size even with large fonts.
 */
public class ActivityDetailsForm extends BaseForm {
    private final String activityId;
    private final Container detailsContainer = new Container(BoxLayout.y());
    private final GeoapifyMapView mapView;
    private final Button externalMapsButton;

    /**
     * @param context shared runtime context
     * @param previousForm previous form for back navigation
     * @param activity activity to display
     */
    public ActivityDetailsForm(AppContext context, Form previousForm, Activity activity) {
        super(context, "activity.details.title", previousForm);
        this.activityId = activity.id();
        this.mapView = new GeoapifyMapView(context);
        this.externalMapsButton = new Button(context.text("map.open.external"));
        detailsContainer.setName("activityDetailsContainer");
        externalMapsButton.setName("externalMapsButton");
        externalMapsButton.setUIID("PrimaryActionButton");
        externalMapsButton.addActionListener(evt -> openExternalMaps());
        Button actionsButton = new Button(context.text("activity.actions"));
        actionsButton.setName("activityActionsButton");
        actionsButton.addActionListener(evt -> new ActivityActionsSheet(context, this, currentActivity()).show());

        Container scrollableDetails = new Container(BoxLayout.y());
        scrollableDetails.setScrollableY(true);
        scrollableDetails.add(detailsContainer);

        Container south = new Container(BoxLayout.y());
        south.add(mapView);
        south.add(actionsButton);
        south.add(externalMapsButton);

        add(BorderLayout.CENTER, scrollableDetails);
        add(BorderLayout.SOUTH, south);
        addShowListener(evt -> refresh());
        refresh();
    }

    /**
     * Rebuilds the details from the latest store state.
     * <p>
     * This method is called after edits and completion toggles so the screen always reflects the
     * canonical store content, not stale constructor arguments.
     */
    public void refresh() {
        Activity activity = currentActivity();
        if (activity == null) {
            Dialog.show(context.text("error.title"), context.text("activity.missing"), context.text("ok"), null);
            context.showHome();
            return;
        }
        detailsContainer.removeAll();
        detailsContainer.add(titleValue(context.text("activity.title"), activity.title(), "detailTitleValue"));
        detailsContainer.add(titleValue(context.text("activity.category"), context.text(activity.category().labelKey()), "detailCategoryValue"));
        detailsContainer.add(titleValue(context.text("activity.date"), activity.hasDate() ? DateTimeUtil.formatDate(activity.date()) : context.text("activity.none"), "detailDateValue"));
        detailsContainer.add(titleValue(context.text("activity.time"), activity.hasTime() ? DateTimeUtil.formatTime(activity.time()) : context.text("activity.none"), "detailTimeValue"));
        detailsContainer.add(titleValue(context.text("activity.completed"), activity.completed() ? context.text("yes") : context.text("no"), "detailCompletedValue"));
        detailsContainer.add(titleValue(context.text("activity.place"), activity.hasPlace() ? activity.place().name() : context.text("activity.no.place"), "detailPlaceValue"));
        if (activity.hasPlace() && activity.place().hasAddress()) {
            detailsContainer.add(titleValue(context.text("activity.address"), activity.place().address(), "detailAddressValue"));
        }
        detailsContainer.add(titleValue(context.text("activity.notes"), activity.hasNotes() ? activity.notes() : context.text("activity.no.notes"), "detailNotesValue"));
        mapView.showPlace(activity.place());
        externalMapsButton.setEnabled(activity.hasPlace());
        detailsContainer.revalidate();
        revalidateLater();
        applyAutoShrinkLater();
    }

    /**
     * Opens the editor for the current activity.
     */
    public void editCurrentActivity() {
        context.showActivityForm(this, currentActivity());
    }

    /**
     * Toggles the completion flag in the store and refreshes the screen.
     */
    public void toggleCompleted() {
        context.getActivityStore().toggleCompleted(activityId);
        refresh();
    }

    /**
     * Removes the place association from the current activity.
     */
    public void removePlace() {
        Activity activity = currentActivity();
        if (activity == null || !activity.hasPlace()) {
            return;
        }
        Activity updated = new Activity(
                activity.id(),
                activity.title(),
                activity.category(),
                activity.date(),
                activity.time(),
                activity.notes(),
                activity.completed(),
                null,
                java.time.Instant.now()
        );
        context.getActivityStore().save(updated);
        refresh();
    }

    /**
     * Confirms and deletes the current activity.
     */
    public void deleteCurrentActivity() {
        if (Dialog.show(context.text("activity.delete.title"), context.text("activity.delete.confirm"), context.text("delete"), context.text("cancel"))) {
            context.getActivityStore().delete(activityId);
            context.showHome();
        }
    }

    /**
     * Opens the place in an external map application or browser.
     */
    private void openExternalMaps() {
        Activity activity = currentActivity();
        if (activity == null || !activity.hasPlace()) {
            return;
        }
        PlaceInfo place = activity.place();
        com.codename1.ui.CN.execute(place.openStreetMapUrl());
    }

    /**
     * Looks up the latest store snapshot of the activity shown by this form.
     *
     * @return current activity, or {@code null} when it has been deleted
     */
    private Activity currentActivity() {
        return context.getActivityStore().findById(activityId);
    }

    /**
     * Creates one titled value block for the details view.
     *
     * @param title localized field title
     * @param value rendered field value
     * @param valueName component name used by tests
     * @return configured container
     */
    private Container titleValue(String title, String value, String valueName) {
        Container row = new Container(BoxLayout.y());
        row.setUIID("DetailSection");
        Label titleLabel = new Label(title);
        titleLabel.setUIID("DetailSectionTitle");
        SpanLabel valueLabel = new SpanLabel(value);
        valueLabel.setName(valueName);
        valueLabel.setUIID("DetailSectionValue");
        valueLabel.setTextUIID("DetailSectionValue");
        row.add(titleLabel);
        row.add(valueLabel);
        return row;
    }
}
