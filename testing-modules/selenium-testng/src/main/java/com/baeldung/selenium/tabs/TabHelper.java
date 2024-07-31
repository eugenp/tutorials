package com.baeldung.selenium.tabs;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import javax.annotation.Nonnull;
import java.util.Optional;
import java.util.Set;

/**
 * Helper class for browser tab handling.
 */
public class TabHelper {

    private final WebDriver driver;

    public TabHelper(@Nonnull final WebDriver driver) {
        this.driver = driver;
    }

    /**
     * Switch to the given browser tab.
     *
     * @param destinationWindowHandle the window handle of the destination tab.
     * @return the window handle of the current tab before switching.
     */
    public String switchToTab(@Nonnull final String destinationWindowHandle) {
        final String currentWindowHandle = driver.getWindowHandle();
        driver.switchTo().window(destinationWindowHandle);
        return currentWindowHandle;
    }

    /**
     * Close all browser tabs except the given one.
     *
     * @param windowHandle the window handle of the tab that should stay open.
     */
    public void closeAllTabsExcept(@Nonnull final String windowHandle) {
        for (final String handle : driver.getWindowHandles()) {
            if (!handle.equals(windowHandle)) {
                driver.switchTo().window(handle);
                driver.close();
            }
        }
        driver.switchTo().window(windowHandle);
    }

    /**
     * Close all browser tabs except the current active one.
     */
    public void closeAllTabsExceptCurrent() {
        final String currentWindow = driver.getWindowHandle();
        closeAllTabsExcept(currentWindow);
    }

    /**
     * Open the given link and switch to the new opened tab.
     * If the link is not opened in a new tab then no switch will be performed.
     *
     * @param link By of the link to open.
     * @return the window handle of the tab before switching.
     */
    public String openLinkAndSwitchToNewTab(@Nonnull final By link) {
        final String windowHandle = driver.getWindowHandle();
        final Set<String> windowHandlesBefore = driver.getWindowHandles();

        driver.findElement(link).click();
        final Set<String> windowHandlesAfter = driver.getWindowHandles();
        windowHandlesAfter.removeAll(windowHandlesBefore);

        final Optional<String> newWindowHandle = windowHandlesAfter.stream().findFirst();
        newWindowHandle.ifPresent(s -> driver.switchTo().window(s));

        return windowHandle;
    }
}