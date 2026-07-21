package com.baeldung.cn1tutorial.ui;

import com.baeldung.cn1tutorial.model.Activity;
import com.baeldung.cn1tutorial.service.AppContext;
import com.baeldung.cn1tutorial.util.DateTimeUtil;
import com.codename1.components.FloatingActionButton;
import com.codename1.components.SpanLabel;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.Container;
import com.codename1.ui.FontImage;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.LayeredLayout;
import java.util.List;

/**
 * Home screen showing the activity summary plus one tappable card per activity.
 */
public class HomeForm extends BaseForm {
    private final SpanLabel summaryLabel = new SpanLabel();
    private final Container listContainer = new Container(BoxLayout.y());

    /**
     * @param context shared runtime context
     */
    public HomeForm(AppContext context) {
        super(context, "home.title", null);
        summaryLabel.setUIID("SectionTitle");
        summaryLabel.setName("homeSummaryLabel");
        listContainer.setName("homeActivityList");
        Container center = new Container(BoxLayout.y());
        center.setScrollableY(true);
        center.add(summaryLabel);
        center.add(listContainer);
        add(BorderLayout.CENTER, center);
        addShowListener(evt -> refreshList());
        FloatingActionButton fab = FloatingActionButton.createFAB(FontImage.MATERIAL_ADD, "AddActivity");
        fab.setName("homeAddFab");
        fab.setText(context.text("activity.add"));
        fab.addActionListener(evt -> context.showActivityForm(this, null));
        fab.bindFabToContainer(getContentPane());
        refreshList();
    }

    /**
     * Rebuilds the list from the current store state.
     * <p>
     * The form intentionally recreates the cards rather than diffing them. For a small tutorial app
     * this keeps the code much easier to follow.
     */
    private void refreshList() {
        List<Activity> activities = context.getActivityStore().getActivitiesInInsertionOrder();
        summaryLabel.setText(context.format(
                "home.summary",
                Integer.valueOf(context.getActivityStore().count()),
                Integer.valueOf(context.getActivityStore().completedCount())
        ));
        listContainer.removeAll();
        if (activities.isEmpty()) {
            SpanLabel empty = new SpanLabel(context.text("home.empty"));
            empty.setName("homeEmptyLabel");
            empty.setUIID("EmptyStateLabel");
            listContainer.add(empty);
        } else {
            for (Activity activity : activities) {
                listContainer.add(createCard(activity));
            }
        }
        listContainer.revalidate();
        revalidateLater();
        applyAutoShrinkLater();
    }

    /**
     * Builds one layered activity card plus an invisible overlay button for unified click handling.
     *
     * @param activity activity to display
     * @return configured card
     */
    private Container createCard(Activity activity) {
        Container card = new Container(new LayeredLayout());
        card.setUIID("ActivityCard");
        Container content = new Container(new BorderLayout());

        Label iconLabel = new Label();
        iconLabel.setUIID("ActivityCardIcon");
        FontImage.setMaterialIcon(iconLabel, activity.completed() ? FontImage.MATERIAL_TASK_ALT : FontImage.MATERIAL_EVENT_NOTE, 4f);

        SpanLabel titleLabel = new SpanLabel(activity.title());
        titleLabel.setUIID("ActivityCardTitle");
        titleLabel.setTextUIID("ActivityCardTitle");

        Label categoryLabel = new Label(context.text(activity.category().labelKey()));
        categoryLabel.setUIID("ActivityCardMeta");

        SpanLabel scheduleLabel = new SpanLabel(scheduleSummary(activity));
        scheduleLabel.setUIID("SectionText");
        scheduleLabel.setTextUIID("SectionText");

        String extraDetails = activity.hasPlace()
                ? activity.place().name()
                : activity.hasNotes() ? activity.notes() : context.text("activity.no.extra.details");
        SpanLabel extraDetailsLabel = new SpanLabel(extraDetails);
        extraDetailsLabel.setUIID("SectionText");
        extraDetailsLabel.setTextUIID("SectionText");

        Label statusLabel = new Label(activity.completed() ? context.text("activity.status.completed") : context.text("activity.status.pending"));
        statusLabel.setUIID("ActivityCardMeta");

        Container textColumn = new Container(BoxLayout.y());
        textColumn.add(titleLabel);
        textColumn.add(categoryLabel);
        textColumn.add(scheduleLabel);
        textColumn.add(extraDetailsLabel);
        textColumn.add(statusLabel);

        Button overlay = createCardOverlayButton("activityCard-" + activity.id(), () -> context.showActivityDetails(this, activity));
        installCardPressFeedback(card, overlay);

        content.add(BorderLayout.WEST, iconLabel);
        content.add(BorderLayout.CENTER, textColumn);

        LayeredLayout layout = (LayeredLayout) card.getLayout();
        card.add(content);
        card.add(overlay);
        layout.setInsets(content, "0 0 0 0");
        layout.setInsets(overlay, "0 0 0 0");
        return card;
    }

    /**
     * Creates a transparent overlay button that makes the whole card tappable.
     * <p>
     * A lead component would interfere with scroll gestures in CN1 scrollable containers, so the
     * overlay approach gives us a single hit target without breaking mouse-wheel scrolling.
     *
     * @param name component name used by tests
     * @param action action to run when tapped
     * @return configured overlay button
     */
    private Button createCardOverlayButton(String name, Runnable action) {
        Button overlay = new Button();
        int dragThreshold = CN.convertToPixels(1.5f);
        int[] pressX = new int[1];
        int[] pressY = new int[1];
        boolean[] dragGesture = new boolean[1];
        overlay.setName(name);
        overlay.setText("");
        overlay.addPointerPressedListener(evt -> {
            pressX[0] = evt.getX();
            pressY[0] = evt.getY();
            dragGesture[0] = false;
        });
        overlay.addPointerDraggedListener(evt -> {
            if (Math.abs(evt.getX() - pressX[0]) > dragThreshold
                    || Math.abs(evt.getY() - pressY[0]) > dragThreshold) {
                dragGesture[0] = true;
            }
        });
        overlay.addActionListener(evt -> {
            if (!dragGesture[0]) {
                action.run();
            }
        });
        overlay.setFocusable(false);
        overlay.setRippleEffect(false);
        clearOverlayButtonStyle(overlay.getAllStyles());
        clearOverlayButtonStyle(overlay.getPressedStyle());
        clearOverlayButtonStyle(overlay.getSelectedStyle());
        clearOverlayButtonStyle(overlay.getDisabledStyle());
        return overlay;
    }

    /**
     * Changes the card UIID while the overlay is pressed.
     *
     * @param card card to restyle
     * @param overlay overlay button handling the pointer events
     */
    private void installCardPressFeedback(Container card, Button overlay) {
        overlay.addPointerPressedListener(evt -> {
            card.setUIID("ActivityCardPressed");
            card.repaint();
        });
        overlay.addPointerDraggedListener(evt -> {
            card.setUIID("ActivityCard");
            card.repaint();
        });
        overlay.addPointerReleasedListener(evt -> {
            card.setUIID("ActivityCard");
            card.repaint();
        });
    }

    /**
     * Removes all visual chrome from the overlay button so only the underlying card is visible.
     *
     * @param style one of the overlay button styles
     */
    private void clearOverlayButtonStyle(Style style) {
        style.setBgTransparency(0);
        style.setBgColor(0);
        style.setBorder(Border.createEmpty());
        style.setMargin(0, 0, 0, 0);
        style.setPadding(0, 0, 0, 0);
    }

    /**
     * Builds the text shown for the date/time row in the card.
     *
     * @param activity activity being rendered
     * @return human-readable schedule summary
     */
    private String scheduleSummary(Activity activity) {
        String date = activity.hasDate() ? DateTimeUtil.formatDate(activity.date()) : "";
        String time = activity.hasTime() ? DateTimeUtil.formatTime(activity.time()) : "";
        if (date.length() > 0 && time.length() > 0) {
            return date + " - " + time;
        }
        if (date.length() > 0) {
            return date;
        }
        if (time.length() > 0) {
            return time;
        }
        return context.text("activity.unscheduled");
    }
}
