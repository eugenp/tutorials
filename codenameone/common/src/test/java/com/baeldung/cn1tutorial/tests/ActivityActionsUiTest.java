package com.baeldung.cn1tutorial.tests;

import com.baeldung.cn1tutorial.model.Activity;
import com.baeldung.cn1tutorial.model.ActivityCategory;
import com.baeldung.cn1tutorial.model.PlaceInfo;
import com.baeldung.cn1tutorial.ui.ActivityDetailsForm;
import com.baeldung.cn1tutorial.testsupport.UiTestSupport;
import java.time.Instant;

/**
 * Exercises the non-destructive actions offered by the activity details sheet.
 */
public class ActivityActionsUiTest extends AbstractDailyRoutineUiTest {
    /**
     * Seeds one activity, opens its details form and executes the in-place actions.
     */
    @Override
    public boolean runTest() {
        Activity seeded = new Activity(
                "activity-actions",
                "Medication reminder",
                ActivityCategory.MEDICATION,
                null,
                null,
                "Take after breakfast",
                false,
                new PlaceInfo("pharmacy-1", "Local Pharmacy", "Local Pharmacy, Rome, Italy", 41.9, 12.5),
                Instant.parse("2026-05-07T08:30:00Z")
        );
        context.getActivityStore().save(seeded);
        com.codename1.ui.CN.callSeriallyAndWait(() -> context.showActivityDetails(null, seeded));
        waitForFormTitle("Activity Details");
        assertNotNull(findByName("activityActionsButton"), "The details screen should expose the actions entry point");
        ActivityDetailsForm details = (ActivityDetailsForm) com.codename1.ui.CN.getCurrentForm();
        com.codename1.ui.CN.callSeriallyAndWait(details::toggleCompleted);
        waitFor(200);
        assertEqual("Yes", UiTestSupport.componentText("detailCompletedValue"));

        com.codename1.ui.CN.callSeriallyAndWait(details::removePlace);
        waitFor(200);
        assertEqual("No place selected", UiTestSupport.componentText("detailPlaceValue"));
        assertEqual(1, context.getActivityStore().count(), "The seeded activity should still exist after non-destructive actions");
        return true;
    }
}
