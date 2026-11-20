package com.baeldung.cn1tutorial.ui;

import com.baeldung.cn1tutorial.service.AppContext;
import com.codename1.ui.CN;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.FontImage;
import com.codename1.ui.Form;
import com.codename1.ui.Toolbar;
import com.codename1.ui.util.UITimer;
import com.codename1.ui.layouts.BorderLayout;

/**
 * Shared base class for the app forms.
 * <p>
 * It centralizes toolbar setup, side-menu navigation and the auto-shrink hooks so the feature
 * forms can focus on their own content.
 */
public abstract class BaseForm extends Form {
    protected final AppContext context;
    protected final Form previousForm;

    /**
     * Creates a form with the app-wide toolbar conventions already applied.
     *
     * @param context shared runtime context
     * @param titleKey localization key for the form title
     * @param previousForm previous form for back navigation, or {@code null} for top-level forms
     */
    protected BaseForm(AppContext context, String titleKey, Form previousForm) {
        super(context.text(titleKey), new BorderLayout());
        this.context = context;
        this.previousForm = previousForm;
        setScrollableY(false);
        setupToolbar(titleKey);
        AutoShrinkSupport.install(this);
    }

    /**
     * Configures either the back command or the top-level side menu.
     *
     * @param titleKey localization key for the current title
     */
    protected final void setupToolbar(String titleKey) {
        Toolbar toolbar = getToolbar();
        String title = context.text(titleKey);
        setTitle(title);
        toolbar.setTitle(title);
        if (previousForm != null) {
            toolbar.setBackCommand("", evt -> previousForm.showBack());
        } else {
            addSideMenuCommand(toolbar, context.text("menu.home"), FontImage.MATERIAL_HOME, context::showHome);
            addSideMenuCommand(toolbar, context.text("menu.settings"), FontImage.MATERIAL_TUNE, () -> context.showSettings(null));
            addSideMenuCommand(toolbar, context.text("menu.native.logs"), FontImage.MATERIAL_TERMINAL, () -> context.showNativeLogs(null));
            addSideMenuCommand(toolbar, context.text("menu.about"), FontImage.MATERIAL_INFO, () -> context.showAbout(null));
        }
    }

    /**
     * Adds a toolbar side-menu command and registers the generated button with autoshrink.
     * <p>
     * CN1 owns the actual {@code SideCommand} buttons, and in on-top side-menu mode they are not
     * part of the regular form content tree. Registering them lets {@link AutoShrinkSupport}
     * resize their text using the configured side-menu width instead of relying on drawer layout
     * timing.
     *
     * @param toolbar form toolbar
     * @param text localized command text
     * @param icon material icon
     * @param action navigation action
     */
    private void addSideMenuCommand(Toolbar toolbar, String text, char icon, Runnable action) {
        Command command = toolbar.addMaterialCommandToSideMenu(text, icon, evt -> navigateFromSideMenu(action));
        Component commandComponent = toolbar.findCommandComponent(command);
        AutoShrinkSupport.registerSideMenuCommand(this, commandComponent);
    }

    /**
     * Closes the side menu and then executes the navigation action on the EDT.
     *
     * @param action navigation action to run
     */
    private void navigateFromSideMenu(Runnable action) {
        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
            toolbar.closeSideMenu();
        }
        CN.callSerially(action);
    }

    /**
     * Applies auto-shrink immediately to this form.
     */
    protected final void applyAutoShrink() {
        AutoShrinkSupport.apply(this);
    }

    /**
     * Applies auto-shrink on a short timer so layout can settle first.
     */
    protected final void applyAutoShrinkLater() {
        UITimer.timer(30, false, this, () -> AutoShrinkSupport.apply(this));
    }

    /**
     * Resets all auto-shrunk labels in this form to their base fonts.
     */
    protected final void resetAutoShrink() {
        AutoShrinkSupport.reset(this);
    }

    /**
     * Resets and reapplies auto-shrink later.
     */
    public final void resetAutoShrinkLater() {
        AutoShrinkSupport.resetAndApplyLater(this);
    }

    /**
     * Clears cached auto-shrink font state before a theme refresh.
     */
    public final void prepareForThemeRefresh() {
        AutoShrinkSupport.prepareForThemeRefresh(this);
    }
}
