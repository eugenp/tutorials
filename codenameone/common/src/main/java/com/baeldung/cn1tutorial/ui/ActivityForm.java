package com.baeldung.cn1tutorial.ui;

import com.baeldung.cn1tutorial.model.Activity;
import com.baeldung.cn1tutorial.model.ActivityCategory;
import com.baeldung.cn1tutorial.model.ActivityDraft;
import com.baeldung.cn1tutorial.model.PlaceInfo;
import com.baeldung.cn1tutorial.service.AppContext;
import com.baeldung.cn1tutorial.util.DateTimeUtil;
import com.baeldung.cn1tutorial.util.IdGenerator;
import com.codename1.components.SpanLabel;
import com.codename1.components.Switch;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Dialog;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.TextArea;
import com.codename1.ui.TextField;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.spinner.Picker;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Activity creation/editing form.
 * <p>
 * The form keeps all editable state locally, then writes one immutable {@link Activity} back to
 * the store when the user presses Save.
 */
public class ActivityForm extends BaseForm {
    private static final ActivityCategory[] CATEGORIES = ActivityCategory.values();

    private final Activity existingActivity;
    private final TextField titleField = new TextField();
    private final Picker categoryPicker = new Picker();
    private final OptionalPicker datePicker = new OptionalPicker();
    private final OptionalPicker timePicker = new OptionalPicker();
    private final TextArea notesArea = new TextArea(5, 20);
    private final Switch completedSwitch = new Switch();
    private final SpanLabel placeSummary = new SpanLabel();
    private final String datePlaceholder;
    private final String timePlaceholder;
    private PlaceInfo selectedPlace;
    private LocalDate selectedDate;
    private LocalTime selectedTime;

    /**
     * @param context shared runtime context
     * @param previousForm previous form for back navigation
     * @param activity activity to edit, or {@code null} to create a new one
     */
    public ActivityForm(AppContext context, Form previousForm, Activity activity) {
        this(context, previousForm, activity, activity == null ? ActivityDraft.empty() : ActivityDraft.from(activity));
    }

    /**
     * Rebuilds the form from an existing draft.
     * <p>
     * This is used when returning from the place search flow. Reusing the previous form instance
     * after a BrowserComponent-backed form is fragile on some ports, especially JavaScript, while
     * rebuilding from a draft keeps the navigation flow stateless and portable.
     *
     * @param context shared runtime context
     * @param previousForm previous form for back navigation
     * @param activity activity to edit, or {@code null} to create a new one
     * @param draft current editable state
     */
    private ActivityForm(AppContext context, Form previousForm, Activity activity, ActivityDraft draft) {
        super(context, activity == null ? "activity.form.create.title" : "activity.form.edit.title", previousForm);
        this.datePlaceholder = context.text("activity.date.unset");
        this.timePlaceholder = context.text("activity.time.unset");
        this.existingActivity = activity;
        if (draft == null) {
            draft = activity == null ? ActivityDraft.empty() : ActivityDraft.from(activity);
        }
        Container content = new Container(BoxLayout.y());
        content.setScrollableY(true);
        titleField.setHint(context.text("activity.title.hint"));
        titleField.setText(draft.title());
        titleField.setName("titleField");
        titleField.setUIID("EditSectionValue");
        content.add(fieldRow(context.text("activity.title"), titleField));
        configureCategoryPicker(draft.category());
        content.add(fieldRow(context.text("activity.category"), categoryPicker));

        configureDatePicker();
        datePicker.setName("datePicker");
        if (draft.date() != null) {
            chooseDate(draft.date());
        } else {
            clearDateSelection();
        }
        Button clearDate = new Button(context.text("clear"));
        clearDate.setName("clearDateButton");
        clearDate.setTraversable(false);
        clearDate.addActionListener(evt -> clearDateSelection());
        content.add(compositeRow(context.text("activity.date"), datePicker, clearDate));

        configureTimePicker();
        timePicker.setName("timePicker");
        if (draft.time() != null) {
            chooseTime(draft.time());
        } else {
            clearTimeSelection();
        }
        Button clearTime = new Button(context.text("clear"));
        clearTime.setName("clearTimeButton");
        clearTime.setTraversable(false);
        clearTime.addActionListener(evt -> clearTimeSelection());
        content.add(compositeRow(context.text("activity.time"), timePicker, clearTime));

        notesArea.setHint(context.text("activity.notes.hint"));
        notesArea.setText(draft.notes());
        notesArea.setName("notesArea");
        notesArea.setUIID("EditSectionValue");
        notesArea.setGrowByContent(true);
        content.add(fieldRow(context.text("activity.notes"), notesArea));

        completedSwitch.setValue(draft.completed());
        completedSwitch.setName("completedSwitch");
        content.add(fieldRow(context.text("activity.completed"), completedSwitch));

        selectedPlace = draft.place();
        placeSummary.setUIID("EditSectionValue");
        placeSummary.setTextUIID("EditSectionValue");
        placeSummary.setName("placeSummary");
        Button choosePlace = new Button(context.text("activity.choose.place"));
        choosePlace.setName("choosePlaceButton");
        choosePlace.addActionListener(evt -> openPlaceSearch());
        Button clearPlace = new Button(context.text("activity.clear.place"));
        clearPlace.setName("clearPlaceButton");
        clearPlace.addActionListener(evt -> {
            selectedPlace = null;
            refreshPlaceSummary();
        });
        Container placeButtons = new Container(BoxLayout.y());
        placeButtons.add(choosePlace);
        placeButtons.add(clearPlace);
        Container placeSection = editSection();
        placeSection.add(sectionTitle(context.text("activity.place")));
        placeSection.add(placeSummary);
        placeSection.add(placeButtons);
        content.add(placeSection);
        refreshPlaceSummary();

        Button saveButton = new Button(context.text("save"));
        saveButton.setName("saveActivityButton");
        saveButton.setUIID("PrimaryActionButton");
        saveButton.addActionListener(evt -> saveActivity());
        add(BorderLayout.CENTER, content);
        add(BorderLayout.SOUTH, saveButton);
        configureFocusTraversal(saveButton, choosePlace, clearPlace);
    }

    /**
     * Opens the place search sub-form and updates the current draft when a place is confirmed.
     */
    private void openPlaceSearch() {
        ActivityDraft draft = currentDraft();
        ActivityForm backForm = new ActivityForm(context, previousForm, existingActivity, draft);
        new PlaceSearchForm(context, backForm, place -> {
            draft.setPlace(place);
            CN.callSerially(() -> new ActivityForm(context, previousForm, existingActivity, draft).showBack());
        }).show();
    }

    /**
     * Captures the current UI state into a draft before leaving this form.
     *
     * @return mutable draft carrying all values currently visible in the editor
     */
    private ActivityDraft currentDraft() {
        ActivityDraft draft = new ActivityDraft();
        draft.setTitle(titleField.getText());
        draft.setCategory(selectedCategory());
        draft.setDate(selectedDate);
        draft.setTime(selectedTime);
        draft.setNotes(notesArea.getText());
        draft.setCompleted(completedSwitch.isValue());
        draft.setPlace(selectedPlace);
        return draft;
    }

    /**
     * Configures the built-in CN1 lightweight date picker and its shortcut buttons.
     */
    private void configureDatePicker() {
        datePicker.setName("datePicker");
        datePicker.setUIID("EditSectionValue");
        datePicker.setPlaceholder(datePlaceholder);
        datePicker.setType(Display.PICKER_TYPE_DATE);
        datePicker.setUseLightweightPopup(true);
        datePicker.setDate(DateTimeUtil.toDate(LocalDate.now()));
        datePicker.addLightweightPopupButton(context.text("picker.today"),
                () -> datePicker.setDate(DateTimeUtil.toDate(LocalDate.now())),
                Picker.LightweightPopupButtonPlacement.ABOVE_SPINNER,
                Component.CENTER);
        datePicker.addLightweightPopupButton(context.text("picker.tomorrow"),
                () -> datePicker.setDate(DateTimeUtil.toDate(LocalDate.now().plusDays(1))),
                Picker.LightweightPopupButtonPlacement.ABOVE_SPINNER,
                Component.CENTER);
        datePicker.addLightweightPopupButton(context.text("picker.next.week"),
                () -> datePicker.setDate(DateTimeUtil.toDate(LocalDate.now().plusDays(7))),
                Picker.LightweightPopupButtonPlacement.ABOVE_SPINNER,
                Component.CENTER);
        datePicker.addActionListener(evt -> {
            if (isPickerCommitEvent(evt)) {
                chooseDate(DateTimeUtil.toLocalDate(datePicker.getDate()));
            }
        });
    }

    /**
     * Configures the built-in CN1 lightweight time picker and its shortcut button.
     */
    private void configureTimePicker() {
        timePicker.setName("timePicker");
        timePicker.setUIID("EditSectionValue");
        timePicker.setPlaceholder(timePlaceholder);
        timePicker.setType(Display.PICKER_TYPE_TIME);
        timePicker.setUseLightweightPopup(true);
        timePicker.setShowMeridiem(false);
        timePicker.setTime(DateTimeUtil.toPickerTime(currentMinuteTime()));
        timePicker.addLightweightPopupButton(context.text("picker.now"),
                () -> timePicker.setTime(DateTimeUtil.toPickerTime(currentMinuteTime())),
                Picker.LightweightPopupButtonPlacement.ABOVE_SPINNER,
                Component.CENTER);
        timePicker.addActionListener(evt -> {
            if (isPickerCommitEvent(evt)) {
                chooseTime(DateTimeUtil.fromPickerTime(timePicker.getTime()));
            }
        });
    }

    /**
     * Identifies the synthetic event fired by CN1 when a lightweight picker commits a value.
     *
     * @param evt picker action event
     * @return {@code true} for Done/Next/Previous commits, {@code false} for the initial button tap
     */
    private boolean isPickerCommitEvent(com.codename1.ui.events.ActionEvent evt) {
        return evt.getX() == -99 && evt.getY() == -99;
    }

    /**
     * Updates the place summary label according to the current selection.
     */
    private void refreshPlaceSummary() {
        if (selectedPlace == null) {
            placeSummary.setText(context.text("activity.no.place"));
        } else {
            String address = selectedPlace.hasAddress() ? selectedPlace.address() : selectedPlace.name();
            placeSummary.setText(selectedPlace.name() + "\n" + address);
        }
    }

    /**
     * Clears the currently selected date while keeping the picker button visible.
     */
    private void clearDateSelection() {
        selectedDate = null;
        datePicker.setCommitted(false);
        datePicker.setDate(DateTimeUtil.toDate(LocalDate.now()));
        applyAutoShrinkLater();
    }

    /**
     * Clears the currently selected time while keeping the picker button visible.
     */
    private void clearTimeSelection() {
        selectedTime = null;
        timePicker.setCommitted(false);
        timePicker.setTime(DateTimeUtil.toPickerTime(currentMinuteTime()));
        applyAutoShrinkLater();
    }

    /**
     * Stores and renders the chosen date.
     *
     * @param date chosen date
     */
    private void chooseDate(LocalDate date) {
        selectedDate = date;
        datePicker.setDate(DateTimeUtil.toDate(date));
        datePicker.setCommitted(true);
        applyAutoShrinkLater();
    }

    /**
     * Stores and renders the chosen time.
     *
     * @param time chosen time
     */
    private void chooseTime(LocalTime time) {
        selectedTime = time;
        timePicker.setTime(DateTimeUtil.toPickerTime(time));
        timePicker.setCommitted(true);
        applyAutoShrinkLater();
    }

    /**
     * Returns the current local time rounded to whole minutes.
     *
     * @return current time without seconds
     */
    private LocalTime currentMinuteTime() {
        LocalTime now = LocalTime.now();
        return LocalTime.of(now.getHour(), now.getMinute());
    }

    /**
     * Validates the form, persists the activity and navigates to the appropriate next screen.
     */
    private void saveActivity() {
        String title = titleField.getText() == null ? "" : titleField.getText().trim();
        if (title.length() == 0) {
            Dialog.show(context.text("validation.title"), context.text("validation.activity.title"), context.text("ok"), null);
            return;
        }
        ActivityCategory category = selectedCategory();
        ActivityDraft draft = new ActivityDraft();
        draft.setTitle(title);
        draft.setCategory(category);
        if (selectedDate != null) {
            draft.setDate(selectedDate);
        }
        if (selectedTime != null) {
            draft.setTime(selectedTime);
        }
        draft.setNotes(notesArea.getText());
        draft.setCompleted(completedSwitch.isValue());
        draft.setPlace(selectedPlace);
        String id = existingActivity == null ? IdGenerator.newId() : existingActivity.id();
        context.getActivityStore().save(draft.toActivity(id));
        context.showHome();
    }

    /**
     * Creates one vertical label+field row.
     *
     * @param labelText localized field label
     * @param field field component
     * @return configured row
     */
    private Container fieldRow(String labelText, com.codename1.ui.Component field) {
        Container row = editSection();
        row.add(sectionTitle(labelText));
        if (field instanceof Switch) {
            Container switchRow = new Container(new BorderLayout());
            switchRow.add(BorderLayout.WEST, field);
            row.add(switchRow);
        } else {
            row.add(field);
        }
        return row;
    }

    /**
     * Creates a vertical row composed of label, main field and secondary action.
     *
     * @param labelText localized row label
     * @param field main field component
     * @param action secondary action component
     * @return configured row
     */
    private Container compositeRow(String labelText, com.codename1.ui.Component field, com.codename1.ui.Component action) {
        Container row = editSection();
        row.add(sectionTitle(labelText));
        row.add(field);
        row.add(action);
        return row;
    }

    /**
     * Creates a section wrapper for one edit-form field.
     *
     * @return configured section container
     */
    private Container editSection() {
        Container row = new Container(BoxLayout.y());
        row.setUIID("EditSection");
        return row;
    }

    /**
     * Creates the small title label used above edit-form values.
     *
     * @param labelText localized section title
     * @return configured title label
     */
    private Label sectionTitle(String labelText) {
        Label label = new Label(labelText);
        label.setUIID("EditSectionTitle");
        return label;
    }

    /**
     * Wires keyboard and virtual-keyboard traversal through the fields in visual order.
     *
     * @param saveButton final save action
     * @param choosePlace place picker action
     * @param clearPlace secondary clear-place action
     */
    private void configureFocusTraversal(Button saveButton, Button choosePlace, Button clearPlace) {
        Component[] order = new Component[]{
                titleField,
                categoryPicker,
                datePicker,
                timePicker,
                notesArea,
                completedSwitch,
                choosePlace,
                clearPlace,
                saveButton
        };
        for (int i = 0; i < order.length; i++) {
            Component current = order[i];
            current.setPreferredTabIndex(i + 1);
            if (i < order.length - 1) {
                current.setNextFocusDown(order[i + 1]);
                current.setNextFocusRight(order[i + 1]);
            }
            if (i > 0) {
                current.setNextFocusUp(order[i - 1]);
                current.setNextFocusLeft(order[i - 1]);
            }
        }
    }

    /**
     * Configures the category picker using localized labels while keeping the enum as the saved
     * value.
     *
     * @param selected currently selected category
     */
    private void configureCategoryPicker(ActivityCategory selected) {
        categoryPicker.setName("categoryPicker");
        categoryPicker.setUIID("EditSectionValue");
        categoryPicker.setUseLightweightPopup(true);
        categoryPicker.setStrings(localizedCategoryLabels());
        categoryPicker.setSelectedStringIndex(indexOfCategory(selected));
    }

    /**
     * Builds the localized label array used by the string picker.
     *
     * @return labels in the same order as {@link #CATEGORIES}
     */
    private String[] localizedCategoryLabels() {
        String[] labels = new String[CATEGORIES.length];
        for (int i = 0; i < CATEGORIES.length; i++) {
            labels[i] = context.text(CATEGORIES[i].labelKey());
        }
        return labels;
    }

    /**
     * Resolves the selected picker row back to the stable enum stored by the model.
     *
     * @return selected category
     */
    private ActivityCategory selectedCategory() {
        int index = categoryPicker.getSelectedStringIndex();
        if (index < 0 || index >= CATEGORIES.length) {
            return ActivityCategory.ROUTINE;
        }
        return CATEGORIES[index];
    }

    /**
     * Finds the picker index for one category.
     *
     * @param category category to locate
     * @return picker index
     */
    private int indexOfCategory(ActivityCategory category) {
        if (category == null) {
            return indexOfCategory(ActivityCategory.ROUTINE);
        }
        for (int i = 0; i < CATEGORIES.length; i++) {
            if (CATEGORIES[i] == category) {
                return i;
            }
        }
        return indexOfCategory(ActivityCategory.ROUTINE);
    }

    /**
     * Picker variant that can display a placeholder while still keeping a real default wheel value.
     * <p>
     * CN1 pickers need a concrete date/time value to initialize the lightweight wheels. The form,
     * however, must support an intentionally empty date/time. This subclass separates those two
     * concepts: the wheel can hold "today" or "now", while the rendered field still says "No date"
     * or "No time" until the user commits a selection.
     */
    private static final class OptionalPicker extends Picker {
        private String placeholder;
        private boolean committed;

        /**
         * Sets the placeholder shown while no value is committed.
         *
         * @param placeholder placeholder text
         */
        void setPlaceholder(String placeholder) {
            this.placeholder = placeholder;
            updateValue();
        }

        /**
         * Marks whether the current picker value should be rendered as selected.
         *
         * @param committed {@code true} after Done/Next commits a value
         */
        void setCommitted(boolean committed) {
            this.committed = committed;
            updateValue();
        }

        @Override
        protected void updateValue() {
            if (!committed && placeholder != null) {
                setText(placeholder);
                return;
            }
            super.updateValue();
        }
    }
}
