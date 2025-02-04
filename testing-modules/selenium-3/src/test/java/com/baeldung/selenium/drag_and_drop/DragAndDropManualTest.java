package com.baeldung.selenium.drag_and_drop;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class DragAndDropManualTest {

    private WebDriver driver;

    @BeforeEach
    public void setup() {
        driver = new ChromeDriver();
        driver.manage()
            .window()
            .maximize();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void givenTwoElements_whenDragAndDropPerformed_thenElementsSwitched() {
        String url = "http://the-internet.herokuapp.com/drag_and_drop";
        driver.get(url);

        WebElement sourceElement = driver.findElement(By.id("column-a"));
        WebElement targetElement = driver.findElement(By.id("column-b"));

        Actions actions = new Actions(driver);
        actions.dragAndDrop(sourceElement, targetElement)
            .build()
            .perform();
    }

    @Test
    public void givenTwoElements_whenClickAndHoldUsed_thenElementsSwitchedWithControl() {
        String url = "http://the-internet.herokuapp.com/drag_and_drop";
        driver.get(url);

        WebElement sourceElement = driver.findElement(By.id("column-a"));
        WebElement targetElement = driver.findElement(By.id("column-b"));

        Actions actions = new Actions(driver);
        actions.clickAndHold(sourceElement)
            .moveToElement(targetElement)
            .release()
            .build()
            .perform();
    }

    @Test
    public void givenDraggableElement_whenDragAndDropByUsed_thenElementMovedByOffset() {
        String url = "https://jqueryui.com/draggable/";
        driver.get(url);

        driver.switchTo()
            .frame(0);

        WebElement draggable = driver.findElement(By.id("draggable"));

        Actions actions = new Actions(driver);
        actions.dragAndDropBy(draggable, 100, 100)
            .build()
            .perform();

    }
}
