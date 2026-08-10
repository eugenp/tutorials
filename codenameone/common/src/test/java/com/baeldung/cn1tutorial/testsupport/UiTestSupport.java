package com.baeldung.cn1tutorial.testsupport;

import com.codename1.components.SpanLabel;
import com.codename1.testing.TestUtils;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Label;
import com.codename1.ui.Slider;
import com.codename1.ui.TextArea;
import com.codename1.ui.List;
import com.codename1.ui.CN;
import com.codename1.ui.Toolbar;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.spinner.Picker;
import java.util.ArrayList;

/**
 * Convenience helpers layered on top of the CN1 test APIs.
 */
public final class UiTestSupport {
    /**
     * Utility class; do not instantiate.
     */
    private UiTestSupport() {
    }

    /**
     * Extracts text from a named UI component.
     *
     * @param name component name
     * @return component text
     */
    public static String componentText(String name) {
        Component component = TestUtils.findByName(name);
        final String[] text = new String[1];
        CN.callSeriallyAndWait(() -> {
            if (component instanceof SpanLabel) {
                text[0] = ((SpanLabel) component).getText();
                return;
            }
            if (component instanceof TextArea) {
                text[0] = ((TextArea) component).getText();
                return;
            }
            if (component instanceof Label) {
                text[0] = ((Label) component).getText();
                return;
            }
            throw new IllegalArgumentException("Unsupported text component: " + component);
        });
        return text[0];
    }

    /**
     * Selects one list or picker item by index.
     *
     * @param name component name
     * @param index item index
     */
    @SuppressWarnings("rawtypes")
    public static void selectListOffset(String name, int index) {
        Component component = TestUtils.findByName(name);
        CN.callSeriallyAndWait(() -> {
            if (component instanceof Picker) {
                ((Picker) component).setSelectedStringIndex(index);
                return;
            }
            if (component instanceof List) {
                List list = (List) component;
                Object item = list.getModel().getItemAt(index);
                list.setSelectedItem(item);
                return;
            }
            throw new IllegalArgumentException("Component is not a List/Picker: " + name);
        });
        TestUtils.waitFor(100);
    }

    /**
     * Sets a slider progress value.
     *
     * @param name component name
     * @param progress slider progress
     */
    public static void setSliderProgress(String name, int progress) {
        Component component = TestUtils.findByName(name);
        if (!(component instanceof Slider)) {
            throw new IllegalArgumentException("Component is not a Slider: " + name);
        }
        CN.callSeriallyAndWait(() -> ((Slider) component).setProgress(progress));
        TestUtils.waitFor(100);
    }

    /**
     * Clicks one named button using the CN1 test helpers.
     *
     * @param name button name
     */
    public static void clickButtonByName(String name) {
        Component component = TestUtils.findByName(name);
        if (!(component instanceof Button)) {
            throw new IllegalArgumentException("Component is not a Button: " + name);
        }
        TestUtils.clickButtonByName(name);
    }

    /**
     * Taps a component even when it is not a literal {@link Button}.
     * <p>
     * This is useful for custom cards that expose a button overlay or otherwise synthesize their
     * own pointer handling.
     *
     * @param name component name
     */
    public static void tapComponentByName(String name) {
        Component component = waitForComponent(name, 2000);
        if (component == null) {
            throw new IllegalArgumentException("Component not found: " + name);
        }
        if (component instanceof Button) {
            tapButton((Button) component);
            TestUtils.waitFor(150);
            return;
        }
        if (component instanceof Container) {
            Component lead = ((Container) component).getLeadComponent();
            if (lead instanceof Button) {
                tapButton((Button) lead);
                TestUtils.waitFor(150);
                return;
            }
        }
        final float[] x = new float[1];
        final float[] y = new float[1];
        final int[] localX = new int[1];
        final int[] localY = new int[1];
        CN.callSeriallyAndWait(() -> {
            TestUtils.ensureVisible(component);
            x[0] = component.getAbsoluteX() + (component.getWidth() / 2f);
            y[0] = component.getAbsoluteY() + (component.getHeight() / 2f);
            localX[0] = Math.max(1, component.getWidth() / 2);
            localY[0] = Math.max(1, component.getHeight() / 2);
        });
        CN.callSeriallyAndWait(() -> {
            component.pointerPressed(localX[0], localY[0]);
            component.pointerReleased(localX[0], localY[0]);
        });
        TestUtils.waitFor(150);
    }

    /**
     * Sends a realistic pointer tap to a button so pointer listeners run before the action.
     *
     * @param button button to tap
     */
    private static void tapButton(Button button) {
        CN.callSeriallyAndWait(() -> {
            TestUtils.ensureVisible(button);
            int localX = Math.max(1, button.getWidth() / 2);
            int localY = Math.max(1, button.getHeight() / 2);
            button.pointerPressed(localX, localY);
            button.pointerReleased(localX, localY);
        });
    }

    /**
     * Polls until a named component appears.
     *
     * @param name component name
     * @param timeoutMillis polling timeout
     * @return matching component, or {@code null}
     */
    public static Component waitForComponent(String name, int timeoutMillis) {
        int waited = 0;
        while (waited <= timeoutMillis) {
            Component component = TestUtils.findByName(name);
            if (component != null) {
                return component;
            }
            TestUtils.waitFor(100);
            waited += 100;
        }
        return null;
    }

    /**
     * Checks whether the current form exposes a side-menu command with the given label.
     *
     * @param label command label
     * @return {@code true} when found
     */
    public static boolean hasSideMenuCommand(String label) {
        final boolean[] found = new boolean[1];
        CN.callSeriallyAndWait(() -> found[0] = findSideMenuCommand(label) != null);
        return found[0];
    }

    /**
     * Dispatches one side-menu command by label.
     *
     * @param label command label
     */
    public static void invokeSideMenuCommand(String label) {
        final RuntimeException[] error = new RuntimeException[1];
        CN.callSeriallyAndWait(() -> {
            Command command = findSideMenuCommand(label);
            if (command == null) {
                error[0] = new IllegalArgumentException("Side menu command not found: " + label + " available=" + sideMenuLabels());
                return;
            }
            Form current = CN.getCurrentForm();
            current.dispatchCommand(command, new ActionEvent(command, ActionEvent.Type.Command));
        });
        if (error[0] != null) {
            throw error[0];
        }
        TestUtils.waitFor(150);
    }

    /**
     * Locates one side-menu command from the current form or the test toolbar fallback.
     *
     * @param label command label
     * @return matching command, or {@code null}
     */
    private static Command findSideMenuCommand(String label) {
        Form current = CN.getCurrentForm();
        if (current != null) {
            Toolbar toolbar = current.getToolbar();
            if (toolbar != null) {
                for (Command command : toolbar.getSideMenuCommands()) {
                    if (label.equals(command.getCommandName())) {
                        return command;
                    }
                }
            }
        }
        Command[] toolbarCommands = TestUtils.getToolbarCommands();
        if (toolbarCommands != null) {
            for (Command command : toolbarCommands) {
                if (command != null && label.equals(command.getCommandName())) {
                    return command;
                }
            }
        }
        return null;
    }

    /**
     * Returns a readable list of side-menu labels for error messages.
     *
     * @return side-menu labels
     */
    private static String sideMenuLabels() {
        java.util.List<String> labels = new ArrayList<String>();
        Form current = CN.getCurrentForm();
        if (current != null && current.getToolbar() != null) {
            for (Command command : current.getToolbar().getSideMenuCommands()) {
                labels.add(command.getCommandName());
            }
        }
        Command[] toolbarCommands = TestUtils.getToolbarCommands();
        if (toolbarCommands != null) {
            for (Command command : toolbarCommands) {
                if (command != null && !labels.contains(command.getCommandName())) {
                    labels.add(command.getCommandName());
                }
            }
        }
        return labels.toString();
    }
}
