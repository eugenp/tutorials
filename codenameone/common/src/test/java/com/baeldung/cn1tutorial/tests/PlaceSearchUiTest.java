package com.baeldung.cn1tutorial.tests;

import com.baeldung.cn1tutorial.model.Activity;
import com.baeldung.cn1tutorial.model.PlaceSuggestion;
import com.baeldung.cn1tutorial.testsupport.FakePlaceSearchService;
import com.baeldung.cn1tutorial.testsupport.UiTestSupport;
import java.util.ArrayList;
import java.util.List;

/**
 * End-to-end flow covering place search, selection and round-trip back to the activity form.
 */
public class PlaceSearchUiTest extends AbstractDailyRoutineUiTest {
    /**
     * Seeds fake suggestions and verifies the full selection flow.
     */
    @Override
    public boolean runTest() {
        List<PlaceSuggestion> suggestions = new ArrayList<PlaceSuggestion>();
        suggestions.add(new PlaceSuggestion("villa-borghese", "Villa Borghese", "Rome, Italy", 41.9142, 12.4923));
        placeSearchService.setSuggestions(suggestions);

        UiTestSupport.clickButtonByName("homeAddFab");
        waitForFormTitle("Add Activity");
        setText("titleField", "Park walk");
        clickButtonByName("choosePlaceButton");
        waitForFormTitle("Place Search");
        setText("placeQueryField", "villa");
        clickButtonByName("placeSearchButton");
        assertNotNull(UiTestSupport.waitForComponent("placeSuggestion-villa-borghese", 8000),
                "The fake place suggestion should be rendered");
        UiTestSupport.tapComponentByName("placeSuggestion-villa-borghese");
        assertTrue(waitForSelection("Villa Borghese"), "Selecting a suggestion should update the preview label");
        clickButtonByName("usePlaceButton");
        waitForFormTitle("Add Activity");
        assertTrue(UiTestSupport.componentText("placeSummary").indexOf("Villa Borghese") >= 0, "The selected place should return to the activity form");
        clickButtonByName("saveActivityButton");
        waitForFormTitle("Daily Routine");
        Activity saved = context.getActivityStore().getActivitiesInInsertionOrder().get(0);
        assertTrue(saved.hasPlace(), "The selected place should be persisted with the activity");

        UiTestSupport.tapComponentByName("activityCard-" + saved.id());
        waitForFormTitle("Activity Details");
        assertEqual("Villa Borghese", UiTestSupport.componentText("detailPlaceValue"));
        assertEqual("Villa Borghese, Rome, Italy", UiTestSupport.componentText("detailAddressValue"));
        assertNotNull(findByName("mapStaticImage"), "A simulator-friendly map preview should be rendered");
        assertNotNull(findByName("externalMapsButton"), "The external maps action should remain available");
        return true;
    }

    /**
     * Waits until the selection label reflects the chosen suggestion.
     *
     * @param expected expected place name fragment
     * @return {@code true} when the expected text appears before timeout
     */
    private boolean waitForSelection(String expected) {
        for (int attempt = 0; attempt < 20; attempt++) {
            if (UiTestSupport.componentText("placeSelectionLabel").indexOf(expected) >= 0) {
                return true;
            }
            waitFor(100);
        }
        return false;
    }
}
