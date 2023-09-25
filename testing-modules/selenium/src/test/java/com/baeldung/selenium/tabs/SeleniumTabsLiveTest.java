package com.baeldung.selenium.tabs;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WindowType;

import static org.junit.jupiter.api.Assertions.assertEquals;

final class SeleniumTabsLiveTest extends SeleniumTestBase {

    private static final By LINK_TO_ATTRIBUTES_PAGE_XPATH = By.xpath("//a[.='Attributes in new page']");
    private static final By LINK_TO_ALERT_PAGE_XPATH = By.xpath("//a[.='Alerts In A New Window From JavaScript']");

    private static final String MAIN_PAGE_URL = "https://testpages.herokuapp.com/styled/windows-test.html";
    private static final String ATTRIBUTES_PAGE_URL = "https://testpages.herokuapp.com/styled/attributes-test.html";
    private static final String ALERT_PAGE_URL = "https://testpages.herokuapp.com/styled/alerts/alert-test.html";

    @Test
    void givenOneTab_whenOpenTab_thenTwoTabsOpen() {
        //given
        driver.get(MAIN_PAGE_URL);
        assertEquals(1, driver.getWindowHandles().size());

        //when
        driver.switchTo().newWindow(WindowType.TAB);

        //then
        assertEquals(2, driver.getWindowHandles().size());
    }

    @Test
    void givenOneTab_whenOpenLinkInTab_thenTwoTabsOpen() {
        //given
        driver.get(MAIN_PAGE_URL);

        //when
        final String mainWindow = tabHelper.openLinkAndSwitchToNewTab(LINK_TO_ATTRIBUTES_PAGE_XPATH);
        assertEquals(ATTRIBUTES_PAGE_URL, driver.getCurrentUrl());

        //then
        tabHelper.switchToTab(mainWindow);
        assertEquals(MAIN_PAGE_URL, driver.getCurrentUrl());
        assertEquals(2, driver.getWindowHandles().size());
    }

    @Test
    void givenTwoTabs_whenCloseAllExceptMainTab_thenOneTabOpen() {
        //given
        driver.get(MAIN_PAGE_URL);
        final String mainWindow = tabHelper.openLinkAndSwitchToNewTab(LINK_TO_ATTRIBUTES_PAGE_XPATH);
        assertEquals(ATTRIBUTES_PAGE_URL, driver.getCurrentUrl());
        assertEquals(2, driver.getWindowHandles().size());

        //when
        tabHelper.closeAllTabsExcept(mainWindow);

        //then
        assertEquals(1, driver.getWindowHandles().size());
        assertEquals(MAIN_PAGE_URL, driver.getCurrentUrl());
    }

    @Test
    void givenTwoTabs_whenSwitching_thenCorrectTabOpen() {
        //given
        driver.get(MAIN_PAGE_URL);
        final String mainWindow = tabHelper.openLinkAndSwitchToNewTab(LINK_TO_ATTRIBUTES_PAGE_XPATH);
        assertEquals(ATTRIBUTES_PAGE_URL, driver.getCurrentUrl());
        assertEquals(2, driver.getWindowHandles().size());

        //when/then
        final String secondWindow = tabHelper.switchToTab(mainWindow);
        assertEquals(MAIN_PAGE_URL, driver.getCurrentUrl());
        tabHelper.switchToTab(secondWindow);
        assertEquals(ATTRIBUTES_PAGE_URL, driver.getCurrentUrl());
    }

    @Test
    void givenThreeTabs_whenSwitching_thenCorrectTabOpen() {
        //given
        driver.get(MAIN_PAGE_URL);
        final String mainWindow = tabHelper.openLinkAndSwitchToNewTab(LINK_TO_ATTRIBUTES_PAGE_XPATH);
        final String secondWindow = tabHelper.switchToTab(mainWindow);
        tabHelper.openLinkAndSwitchToNewTab(LINK_TO_ALERT_PAGE_XPATH);
        final String thirdWindow = driver.getWindowHandle();
        assertEquals(3, driver.getWindowHandles().size());

        //when/then
        tabHelper.switchToTab(mainWindow);
        assertEquals(MAIN_PAGE_URL, driver.getCurrentUrl());

        tabHelper.switchToTab(secondWindow);
        assertEquals(ATTRIBUTES_PAGE_URL, driver.getCurrentUrl());

        tabHelper.switchToTab(thirdWindow);
        assertEquals(ALERT_PAGE_URL, driver.getCurrentUrl());
    }
}