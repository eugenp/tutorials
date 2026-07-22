package com.baeldung.cn1tutorial.tests;

import com.baeldung.cn1tutorial.testsupport.UiTestSupport;

/**
 * Verifies the settings form updates both localization and persisted font size.
 */
public class SettingsLocalizationUiTest extends AbstractDailyRoutineUiTest {
    /**
     * Changes the language and font scale through the real settings form.
     */
    @Override
    public boolean runTest() throws Exception {
        com.codename1.ui.CN.callSeriallyAndWait(() -> context.showSettings(null));
        waitForFormTitle("Settings");
        UiTestSupport.selectListOffset("settingsLanguagePicker", 1);
        UiTestSupport.setSliderProgress("settingsFontSlider", 4);
        clickButtonByName("settingsApplyButton");
        waitForFormTitle("Impostazioni");
        assertEqual("120%", UiTestSupport.componentText("settingsFontValueLabel"));
        assertEqual("it", context.getSettings().languageCode());
        assertEqual(120, context.getSettings().fontScalePercent());
        assertEqual("it", context.getSettingsRepository().load().languageCode());
        assertEqual(120, context.getSettingsRepository().load().fontScalePercent());
        return true;
    }
}
