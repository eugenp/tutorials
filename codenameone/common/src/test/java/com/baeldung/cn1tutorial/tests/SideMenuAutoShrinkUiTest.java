package com.baeldung.cn1tutorial.tests;

import com.baeldung.cn1tutorial.model.AppSettings;
import com.baeldung.cn1tutorial.model.AppearanceMode;
import com.baeldung.cn1tutorial.ui.AutoShrinkSupport;
import com.codename1.ui.Button;
import com.codename1.ui.CN;
import com.codename1.ui.Command;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;

/**
 * Regression test for autoshrink support on toolbar-owned side-menu buttons.
 */
public class SideMenuAutoShrinkUiTest extends AbstractDailyRoutineUiTest {
    private static final String ORIGINAL_FONT_KEY = "dailyRoutine.autoShrinkOriginalFont";

    /**
     * Verifies that side-menu commands are included in autoshrink processing.
     */
    @Override
    public boolean runTest() {
        CN.callSeriallyAndWait(() -> {
            app.applySettings(new AppSettings("it", 130, AppearanceMode.SYSTEM), false);
            app.showHome();
        });
        waitForFormTitle("Daily Routine");
        waitFor(250);
        assertSideCommandsAreAutoshrunk();
        CN.callSeriallyAndWait(() -> AutoShrinkSupport.resetAndApply(CN.getCurrentForm()));
        assertSideCommandsAreAutoshrunk();
        if (canOpenSideMenuReliably()) {
            CN.callSeriallyAndWait(() -> CN.getCurrentForm().getToolbar().openSideMenu());
            waitFor(500);
            assertVisibleSideCommandsFit();
            CN.callSeriallyAndWait(() -> CN.getCurrentForm().getToolbar().closeSideMenu());
        } else {
            log("Skipping side-menu width assertions in headless/JMF mode.");
        }
        return true;
    }

    /**
     * Checks that each generated side-menu button was registered with {@code AutoShrinkSupport}.
     */
    private void assertSideCommandsAreAutoshrunk() {
        final int[] commandCount = new int[1];
        final String[] detail = new String[1];
        final boolean[] valid = new boolean[]{true};
        CN.callSeriallyAndWait(() -> {
            Toolbar toolbar = CN.getCurrentForm().getToolbar();
            StringBuilder out = new StringBuilder();
            for (Command command : toolbar.getSideMenuCommands()) {
                Button button = toolbar.findCommandComponent(command);
                commandCount[0]++;
                if (button == null) {
                    valid[0] = false;
                    out.append(command.getCommandName()).append(": missing button; ");
                    continue;
                }
                boolean configured = !button.isAutoSizeMode() && button.getClientProperty(ORIGINAL_FONT_KEY) != null;
                if (!configured) {
                    valid[0] = false;
                }
                out.append(button.getText())
                        .append(": autoSize=")
                        .append(button.isAutoSizeMode())
                        .append(", originalFont=")
                        .append(button.getClientProperty(ORIGINAL_FONT_KEY) != null)
                        .append("; ");
            }
            detail[0] = out.toString();
        });
        assertTrue(commandCount[0] > 0, "The current form should expose side-menu commands");
        assertTrue(valid[0], "All side-menu commands should be configured for autoshrink: " + detail[0]);
    }

    /**
     * Checks actual text fit after the drawer has been opened and laid out.
     */
    private void assertVisibleSideCommandsFit() {
        final String[] detail = new String[1];
        final boolean[] valid = new boolean[]{true};
        CN.callSeriallyAndWait(() -> {
            Form current = CN.getCurrentForm();
            Toolbar toolbar = current.getToolbar();
            StringBuilder out = new StringBuilder();
            for (Command command : toolbar.getSideMenuCommands()) {
                Button button = toolbar.findCommandComponent(command);
                if (button == null) {
                    valid[0] = false;
                    out.append(command.getCommandName()).append(": missing button; ");
                    continue;
                }
                int width = button.getWidth();
                int preferred = button.getPreferredW();
                boolean fits = width <= 0 || preferred <= width;
                if (!fits) {
                    valid[0] = false;
                }
                out.append(button.getText())
                        .append(": width=")
                        .append(width)
                        .append(", preferred=")
                        .append(preferred)
                        .append("; ");
            }
            detail[0] = out.toString();
        });
        assertTrue(valid[0], "Visible side-menu command text should fit after autoshrink: " + detail[0]);
    }

    /**
     * Headless/JMF runs can enumerate commands but don't reliably materialize drawer dimensions.
     *
     * @return {@code true} when width assertions are meaningful
     */
    private boolean canOpenSideMenuReliably() {
        return !Boolean.getBoolean("java.awt.headless")
                && !"jmf".equalsIgnoreCase(System.getProperty("cn1.javase.implementation", ""));
    }
}
