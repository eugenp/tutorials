package com.baeldung.cucumber_tags.acceptance.ui.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RandomNumberGeneratorPage extends Page {

    @FindBy(id = "min")
    private WebElement minField;

    @FindBy(id = "max")
    private WebElement maxField;

    @FindBy(id = "generate")
    private WebElement generateButton;

    @FindBy(id = "result")
    private WebElement result;

    public RandomNumberGeneratorPage(WebDriver driver) {
        super(driver, "Random Number Generator");
    }

    public void enterMinField(String min) {
        minField.sendKeys(min);
    }

    public void enterMaxField(String max) {
        maxField.sendKeys(max);
    }

    public void pressGenerateButton() {
        generateButton.click();
    }

    public String getResult() {
        return result.getText();
    }
}

