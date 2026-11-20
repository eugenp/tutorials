package com.baeldung.cn1tutorial.tests;

import com.baeldung.cn1tutorial.model.Activity;
import com.baeldung.cn1tutorial.testsupport.UiTestSupport;
import com.codename1.ui.CN;
import com.codename1.ui.Component;

/**
 * End-to-end creation flow for a basic activity.
 */
public class ActivityCreationUiTest extends AbstractDailyRoutineUiTest {
    /**
     * Creates an activity from the home screen, returns to home and verifies the saved details.
     */
    @Override
    public boolean runTest() {
        UiTestSupport.clickButtonByName("homeAddFab");
        waitForFormTitle("Add Activity");
        setText("titleField", "Morning walk");
        setText("notesArea", "Bring water");
        UiTestSupport.selectListOffset("categoryPicker", 4);
        clickButtonByName("saveActivityButton");
        waitForFormTitle("Daily Routine");
        assertEqual(1, context.getActivityStore().count(), "The new activity should be persisted in the store");

        Activity saved = context.getActivityStore().getActivitiesInInsertionOrder().get(0);
        Component cardOverlay = findByName("activityCard-" + saved.id());
        assertNotNull(cardOverlay, "The saved activity should be visible as a home card");
        CN.callSeriallyAndWait(() -> {
            cardOverlay.pointerPressed(5, 5);
            cardOverlay.pointerDragged(5, CN.convertToPixels(8f));
            cardOverlay.pointerReleased(5, CN.convertToPixels(8f));
        });
        waitFor(150);
        waitForFormTitle("Daily Routine");

        UiTestSupport.tapComponentByName("activityCard-" + saved.id());
        waitForFormTitle("Activity Details");
        assertEqual("Morning walk", UiTestSupport.componentText("detailTitleValue"));
        assertEqual("Exercise", UiTestSupport.componentText("detailCategoryValue"));
        assertEqual("Bring water", UiTestSupport.componentText("detailNotesValue"));
        assertEqual("No place selected", UiTestSupport.componentText("detailPlaceValue"));
        return true;
    }
}
