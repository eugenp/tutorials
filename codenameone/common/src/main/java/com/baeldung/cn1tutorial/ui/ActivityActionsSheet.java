package com.baeldung.cn1tutorial.ui;

import com.baeldung.cn1tutorial.model.Activity;
import com.baeldung.cn1tutorial.service.AppContext;
import com.codename1.ui.Button;
import com.codename1.ui.Sheet;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;

/**
 * Bottom sheet exposing activity actions from the details screen.
 */
public class ActivityActionsSheet extends Sheet {
    /**
     * @param context shared runtime context
     * @param host details form that owns the current activity
     * @param activity activity whose actions are shown
     */
    public ActivityActionsSheet(AppContext context, ActivityDetailsForm host, Activity activity) {
        super(null, context.text("activity.actions"));
        setPosition(BorderLayout.SOUTH, BorderLayout.EAST);
        getContentPane().setLayout(BoxLayout.y());

        Button edit = new Button(context.text("activity.action.edit"));
        edit.setName("activityActionEdit");
        edit.setUIID("ActivityActionButton");
        edit.addActionListener(evt -> closeThen(host::editCurrentActivity));
        Button toggle = new Button(activity.completed() ? context.text("activity.action.mark.pending") : context.text("activity.action.mark.complete"));
        toggle.setName("activityActionToggle");
        toggle.setUIID("ActivityActionButton");
        toggle.addActionListener(evt -> closeThen(host::toggleCompleted));
        Button removePlace = new Button(context.text("activity.action.remove.place"));
        removePlace.setName("activityActionRemovePlace");
        removePlace.setUIID("ActivityActionButton");
        removePlace.setEnabled(activity.hasPlace());
        removePlace.addActionListener(evt -> closeThen(host::removePlace));
        Button delete = new Button(context.text("activity.action.delete"));
        delete.setName("activityActionDelete");
        delete.setUIID("ActivityActionButton");
        delete.addActionListener(evt -> closeThen(host::deleteCurrentActivity));

        getContentPane().addAll(edit, toggle, removePlace, delete);
    }

    /**
     * Runs the action only after the sheet has actually closed.
     * <p>
     * This avoids visual leftovers when the user later navigates back to the underlying form.
     *
     * @param action action to execute after closing
     */
    private void closeThen(Runnable action) {
        addCloseListener(evt -> action.run());
        back(80);
    }
}
