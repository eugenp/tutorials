package com.baeldung.selenide;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PageObjectsLiveTest {
    @Test
    public void searchBaeldung() {
        SearchFormPage searchFormPage = new SearchFormPage();
        searchFormPage.open();
        searchFormPage.search("Baeldung");

        SearchResultsPage results = new SearchResultsPage();

        SearchResult firstResult = results.getResult(0);
        assertTrue(firstResult.getText().contains("Baeldung"));
        assertTrue(firstResult.getText().contains("In-depth, to-the-point tutorials on Java, Spring, Spring Boot, Security, and REST."));
    }

    public class SearchFormPage {
        public void open() {
            Selenide.open("http://duckduckgo.com/");
        }

        public void search(String term) {
            SelenideElement searchbox = $(By.id("searchbox_input"));
            searchbox.click();
            searchbox.sendKeys(term);
            searchbox.pressEnter();
        }
    }

    public class SearchResultsPage {
        public SearchResult getResult(int index) {
            SelenideElement result = $(By.id("r1-" + index));

            result.shouldBe(visible);

            return new SearchResult(result);
        }
    }

    public class SearchResult {
        private SelenideElement result;

        public SearchResult(SelenideElement result) {
            this.result = result;
        }

        public String getText() {
            return result.getText();
        }
    }
}
