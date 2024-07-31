package com.baeldung.selenide;

import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Condition.*;

public class SearchLiveTest {

    @Test
    public void searchBaeldung() throws Exception {
        open("https://duckduckgo.com/");

        SelenideElement searchbox = $(By.id("searchbox_input"));
        searchbox.click();
        searchbox.sendKeys("Baeldung");
        searchbox.pressEnter();

        SelenideElement firstResult = $(By.id("r1-0"));
        firstResult.shouldHave(text("Baeldung"));
        firstResult.shouldHave(text("In-depth, to-the-point tutorials on Java, Spring, Spring Boot, Security, and REST."));
    }

    @Test
    public void searchBaeldungFailing() throws Exception {
        open("https://duckduckgo.com/");

        SelenideElement searchbox = $(By.id("searchbox_input"));
        searchbox.click();
        searchbox.sendKeys("Something Else");
        searchbox.pressEnter();

        SelenideElement firstResult = $(By.id("r1-0"));
        firstResult.shouldHave(text("Baeldung"));
        firstResult.shouldHave(text("In-depth, to-the-point tutorials on Java, Spring, Spring Boot, Security, and REST."));
    }
}
