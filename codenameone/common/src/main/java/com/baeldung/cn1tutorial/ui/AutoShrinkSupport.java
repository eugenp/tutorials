package com.baeldung.cn1tutorial.ui;

import com.codename1.components.SpanLabel;
import com.codename1.ui.CN;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Form;
import com.codename1.ui.Image;
import com.codename1.ui.Label;
import com.codename1.ui.Toolbar;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.codename1.ui.util.UITimer;
import java.util.ArrayList;
import java.util.List;

/**
 * Applies one-way automatic font shrinking to CN1 labels.
 * <p>
 * CN1 already offers {@link Label#setAutoSizeMode(boolean)}, but the tutorial needs a more
 * conservative variant: never grow text beyond the font chosen by the theme, only shrink it when
 * large accessibility settings would otherwise clip single-line content.
 */
public final class AutoShrinkSupport {
    private static final String CONFIGURED_KEY = "dailyRoutine.autoShrinkConfigured";
    private static final String ORIGINAL_FONT_KEY = "dailyRoutine.autoShrinkOriginalFont";
    private static final String INSTALLED_KEY = "dailyRoutine.autoShrinkInstalled";
    private static final String SIDE_MENU_COMMANDS_KEY = "dailyRoutine.autoShrinkSideMenuCommands";
    private static final float MIN_MM = 1.6f;
    private static final float MIN_RATIO = 0.5f;
    private static final float SIDE_MENU_SAFETY_MM = 2f;

    /**
     * Utility class; do not instantiate.
     */
    private AutoShrinkSupport() {
    }

    /**
     * Installs lifecycle hooks on a form so auto-shrink can re-run when the form is shown or
     * resized.
     *
     * @param form form to instrument
     */
    public static void install(Form form) {
        if (form == null || Boolean.TRUE.equals(form.getClientProperty(INSTALLED_KEY))) {
            return;
        }
        form.putClientProperty(INSTALLED_KEY, Boolean.TRUE);
        form.addShowListener(evt -> resetAndApplyLater(form));
        form.addSizeChangedListener(evt -> resetAndApplyLater(form));
    }

    /**
     * Applies auto-shrink recursively starting from one component.
     *
     * @param root root component
     */
    public static void apply(Component root) {
        if (root == null) {
            return;
        }
        visit(root);
    }

    /**
     * Applies auto-shrink to a form and also to its toolbar title component.
     *
     * @param form form to process
     */
    public static void apply(Form form) {
        if (form == null) {
            return;
        }
        apply((Component) form);
        Component titleComponent = titleComponent(form);
        if (titleComponent != null) {
            apply(titleComponent);
        }
        shrinkToolbarTitle(form);
        shrinkSideMenuCommands(form);
    }

    /**
     * Resets auto-shrink state recursively starting from one component.
     *
     * @param root root component
     */
    public static void reset(Component root) {
        reset(root, false);
    }

    /**
     * Resets a form and its toolbar title component.
     *
     * @param form form to reset
     */
    public static void reset(Form form) {
        if (form == null) {
            return;
        }
        reset((Component) form);
        Component titleComponent = titleComponent(form);
        if (titleComponent != null) {
            reset(titleComponent);
        }
        resetSideMenuCommands(form, false);
    }

    /**
     * Clears cached font information before a theme refresh.
     *
     * @param root root component
     */
    public static void prepareForThemeRefresh(Component root) {
        reset(root, true);
    }

    /**
     * Clears cached font information for a whole form before a theme refresh.
     *
     * @param form form to prepare
     */
    public static void prepareForThemeRefresh(Form form) {
        if (form == null) {
            return;
        }
        prepareForThemeRefresh((Component) form);
        Component titleComponent = titleComponent(form);
        if (titleComponent != null) {
            prepareForThemeRefresh(titleComponent);
        }
        resetSideMenuCommands(form, true);
    }

    /**
     * Resets and reapplies auto-shrink immediately.
     *
     * @param form form to refresh
     */
    public static void resetAndApply(Form form) {
        if (form == null) {
            return;
        }
        refreshSideMenuCommands(form);
        reset(form);
        form.revalidate();
        apply(form);
        form.revalidate();
        form.repaint();
    }

    /**
     * Registers one toolbar-owned side-menu command for deterministic text shrinking.
     * <p>
     * CN1 keeps on-top side-menu buttons outside the normal form content tree. These commands also
     * don't have a stable width until the drawer is opened, so they use a manual calculation based
     * on the same side-menu width constants used internally by {@link Toolbar}.
     *
     * @param form owning form
     * @param component generated {@code SideCommand} button
     */
    public static void registerSideMenuCommand(Form form, Component component) {
        if (form == null || component == null) {
            return;
        }
        List<Component> components = sideMenuCommands(form, true);
        if (!components.contains(component)) {
            components.add(component);
        }
    }

    /**
     * Resets and reapplies auto-shrink later, using two short passes so CN1 layouts and peer
     * components have time to settle first.
     *
     * @param form form to refresh
     */
    public static void resetAndApplyLater(Form form) {
        if (form == null) {
            return;
        }
        UITimer.timer(30, false, form, () -> {
            resetAndApply(form);
            UITimer.timer(30, false, form, () -> {
                apply(form);
                form.revalidate();
                form.repaint();
            });
        });
    }

    /**
     * Synchronizes the toolbar title component with the form title after a theme refresh.
     *
     * @param form form whose title should be refreshed
     */
    public static void refreshTitleComponent(Form form) {
        if (form == null) {
            return;
        }
        Toolbar toolbar = form.getToolbar();
        if (toolbar == null) {
            return;
        }
        String title = form.getTitle();
        if (title != null) {
            toolbar.setTitle(title);
        }
    }

    /**
     * Traverses the component tree and configures eligible labels.
     *
     * @param component component to visit
     */
    private static void visit(Component component) {
        if (component instanceof SpanLabel) {
            // SpanLabel already wraps across multiple lines, so shrinking it would be counterproductive.
        } else if (component instanceof Label) {
            configure((Label) component);
        }
        if (component instanceof Container) {
            Container container = (Container) component;
            for (Component child : container) {
                visit(child);
            }
        }
    }

    /**
     * Traverses the component tree and clears the auto-shrink state.
     *
     * @param component component to reset
     * @param clearOriginalFont whether the cached base font should also be forgotten
     */
    private static void reset(Component component, boolean clearOriginalFont) {
        if (component instanceof Label) {
            resetLabel((Label) component, clearOriginalFont);
        }
        if (component instanceof Container) {
            Container container = (Container) component;
            for (Component child : container) {
                reset(child, clearOriginalFont);
            }
        }
    }

    /**
     * Configures one eligible label for one-way shrinking.
     *
     * @param label label to configure
     */
    private static void configure(Label label) {
        String text = label.getText();
        if (text == null || text.trim().length() == 0 || text.indexOf('\n') >= 0) {
            return;
        }
        Font font = (Font) label.getClientProperty(ORIGINAL_FONT_KEY);
        if (font == null) {
            font = label.getUnselectedStyle().getFont();
            font = prepareBaseFont(font);
            if (font != null) {
                label.putClientProperty(ORIGINAL_FONT_KEY, font);
            }
        }
        if (font == null) {
            return;
        }
        if (Boolean.TRUE.equals(label.getClientProperty(CONFIGURED_KEY))) {
            return;
        }
        float pixelSize = font.getPixelSize();
        if (pixelSize < 1f) {
            pixelSize = font.getHeight();
            if (pixelSize < 1f) {
                return;
            }
        }
        float pixelsPerMm = Math.max(1f, CN.convertToPixels(1f));
        float maxMm = pixelSize / pixelsPerMm;
        float minMm = Math.max(MIN_MM, maxMm * MIN_RATIO);
        if (minMm >= maxMm) {
            minMm = Math.max(1f, maxMm - 0.1f);
        }
        label.getAllStyles().setFont(font);
        label.setMaxAutoSize(maxMm);
        label.setMinAutoSize(minMm);
        label.setAutoSizeMode(true);
        label.putClientProperty(CONFIGURED_KEY, Boolean.TRUE);
        refreshLabelMetrics(label);
    }

    /**
     * Resets one label to its original font and clears the configuration marker.
     *
     * @param label label to reset
     * @param clearOriginalFont whether the cached base font should also be removed
     */
    private static void resetLabel(Label label, boolean clearOriginalFont) {
        label.setAutoSizeMode(false);
        label.putClientProperty(CONFIGURED_KEY, null);
        if (clearOriginalFont) {
            label.putClientProperty(ORIGINAL_FONT_KEY, null);
            refreshLabelMetrics(label);
            return;
        }
        Font originalFont = (Font) label.getClientProperty(ORIGINAL_FONT_KEY);
        if (originalFont != null) {
            label.getAllStyles().setFont(originalFont);
        }
        refreshLabelMetrics(label);
    }

    /**
     * Ensures the base font has a usable pixel size for CN1 auto-size calculations.
     *
     * @param font candidate base font
     * @return usable font, or {@code null}
     */
    private static Font prepareBaseFont(Font font) {
        if (font == null) {
            return null;
        }
        if (font.getPixelSize() >= 1f) {
            return font;
        }
        float fontHeight = font.getHeight();
        if (fontHeight < 1f) {
            return null;
        }
        try {
            return font.derive(fontHeight, font.getStyle());
        } catch (RuntimeException ex) {
            return null;
        }
    }

    /**
     * Forces CN1 to invalidate the cached width metrics for one label.
     *
     * @param label label to refresh
     */
    private static void refreshLabelMetrics(Label label) {
        String text = label.getText();
        if (text != null) {
            label.setText(text);
        }
        label.setShouldCalcPreferredSize(true);
        label.repaint();
    }

    /**
     * Returns the toolbar title component, when present.
     *
     * @param form form whose toolbar should be queried
     * @return title component, or {@code null}
     */
    private static Component titleComponent(Form form) {
        Toolbar toolbar = form.getToolbar();
        return toolbar == null ? null : toolbar.getTitleComponent();
    }

    /**
     * Shrinks the toolbar title deterministically after the toolbar has a concrete width.
     * <p>
     * {@link Label#setAutoSizeMode(boolean)} runs from the label layout hook. Toolbar title labels
     * can be configured after that hook has already run, so a direct width-based pass avoids clipped
     * titles until the next resize.
     *
     * @param form owner form
     */
    private static void shrinkToolbarTitle(Form form) {
        Component component = titleComponent(form);
        if (!(component instanceof Label)) {
            return;
        }
        Label label = (Label) component;
        int availableWidth = label.getWidth();
        if (availableWidth <= 0) {
            return;
        }
        Style style = label.getStyle();
        int availableTextWidth = availableWidth - style.getHorizontalPadding();
        shrinkLabelToWidth(label, Math.max(0, availableTextWidth));
    }

    /**
     * Applies deterministic shrinking to side-menu commands.
     * <p>
     * Unlike regular labels, side-menu buttons are laid out inside a toolbar-owned drawer. Their
     * runtime width can be unavailable while the form is shown, so this method predicts the text
     * width from CN1's side-menu theme constants instead of using {@link Label#setAutoSizeMode(boolean)}.
     *
     * @param form owner form
     */
    private static void shrinkSideMenuCommands(Form form) {
        List<Component> components = sideMenuCommands(form, false);
        if (components == null) {
            return;
        }
        int availableTextWidth = sideMenuTextWidth(form);
        if (availableTextWidth <= 0) {
            return;
        }
        for (Component component : components) {
            if (component instanceof Label) {
                shrinkLabelToWidth((Label) component, availableTextWidth);
            }
        }
    }

    /**
     * Shrinks one single-line label if its text exceeds the available width.
     *
     * @param label label to shrink
     * @param availableTextWidth maximum text width in pixels
     */
    private static void shrinkLabelToWidth(Label label, int availableTextWidth) {
        String text = label.getText();
        if (text == null || text.trim().length() == 0 || text.indexOf('\n') >= 0) {
            return;
        }
        Font baseFont = (Font) label.getClientProperty(ORIGINAL_FONT_KEY);
        if (baseFont == null) {
            baseFont = prepareBaseFont(label.getUnselectedStyle().getFont());
            if (baseFont != null) {
                label.putClientProperty(ORIGINAL_FONT_KEY, baseFont);
            }
        }
        if (baseFont == null) {
            return;
        }
        label.setAutoSizeMode(false);
        label.getAllStyles().setFont(shrinkFontToWidth(baseFont, text, availableTextWidth));
        label.putClientProperty(CONFIGURED_KEY, Boolean.TRUE);
        refreshLabelMetrics(label);
    }

    /**
     * Derives a smaller font only when the text would otherwise exceed the available width.
     *
     * @param baseFont font selected by the current theme and font scale
     * @param text text to measure
     * @param availableTextWidth maximum text width in pixels
     * @return original or derived font
     */
    private static Font shrinkFontToWidth(Font baseFont, String text, int availableTextWidth) {
        float pixelSize = baseFont.getPixelSize();
        if (pixelSize < 1f) {
            pixelSize = baseFont.getHeight();
            if (pixelSize < 1f) {
                return baseFont;
            }
        }
        Font currentFont = baseFont;
        int minPixelSize = Math.max(CN.convertToPixels(MIN_MM), (int) (pixelSize * MIN_RATIO));
        while (currentFont.stringWidth(text) > availableTextWidth && pixelSize > minPixelSize) {
            pixelSize = Math.max(minPixelSize, pixelSize - 1f);
            currentFont = baseFont.derive(pixelSize, baseFont.getStyle());
        }
        return currentFont;
    }

    /**
     * Calculates the text space available inside one side-menu command.
     *
     * @param form owner form
     * @return available text width in pixels
     */
    private static int sideMenuTextWidth(Form form) {
        int width = sideMenuWidth();
        List<Component> components = sideMenuCommands(form, false);
        if (components != null && !components.isEmpty() && components.get(0) instanceof Label) {
            Label label = (Label) components.get(0);
            Style style = label.getStyle();
            width -= style.getHorizontalMargins();
            width -= style.getHorizontalPadding();
            Image icon = label.getIcon();
            if (icon != null && (label.getTextPosition() == Label.RIGHT || label.getTextPosition() == Label.LEFT)) {
                width -= icon.getWidth();
                width -= label.getGap();
            }
        }
        width -= CN.convertToPixels(SIDE_MENU_SAFETY_MM);
        return Math.max(0, width);
    }

    /**
     * Mirrors CN1's {@code Toolbar.showOnTopSidemenuImpl()} width calculation.
     *
     * @return predicted side-menu drawer width in pixels
     */
    private static int sideMenuWidth() {
        int displayWidth = CN.getDisplayWidth();
        boolean portrait = CN.isPortrait();
        boolean tablet = CN.isTablet();
        String key;
        if (portrait) {
            key = tablet ? "sideMenuSizeTabPortraitInt" : "sideMenuSizePortraitInt";
        } else {
            key = tablet ? "sideMenuSizeTabLandscapeInt" : "sideMenuSizeLandscapeInt";
        }
        int constant = UIManager.getInstance().getThemeConstant(key, -1);
        if (constant >= 0) {
            return displayWidth * (100 - constant) / 100;
        }
        if (portrait) {
            return tablet ? displayWidth * 2 / 3 : displayWidth - CN.convertToPixels(10f);
        }
        return tablet ? displayWidth * 3 / 4 : displayWidth * 4 / 10;
    }

    /**
     * Resets registered side-menu command fonts.
     *
     * @param form owner form
     * @param clearOriginalFont whether cached base fonts should be cleared
     */
    private static void resetSideMenuCommands(Form form, boolean clearOriginalFont) {
        List<Component> components = sideMenuCommands(form, false);
        if (components == null) {
            return;
        }
        for (Component component : components) {
            reset(component, clearOriginalFont);
        }
    }

    /**
     * Refreshes registered side-menu commands so they pick up theme/font-scale changes.
     *
     * @param form owner form
     */
    private static void refreshSideMenuCommands(Form form) {
        List<Component> components = sideMenuCommands(form, false);
        if (components == null) {
            return;
        }
        for (Component component : components) {
            component.refreshTheme();
        }
    }

    /**
     * Returns the per-form list of registered side-menu command components.
     *
     * @param form owner form
     * @param create whether to create the list when absent
     * @return component list, or {@code null}
     */
    @SuppressWarnings("unchecked")
    private static List<Component> sideMenuCommands(Form form, boolean create) {
        List<Component> components = (List<Component>) form.getClientProperty(SIDE_MENU_COMMANDS_KEY);
        if (components == null && create) {
            components = new ArrayList<Component>();
            form.putClientProperty(SIDE_MENU_COMMANDS_KEY, components);
        }
        return components;
    }
}
