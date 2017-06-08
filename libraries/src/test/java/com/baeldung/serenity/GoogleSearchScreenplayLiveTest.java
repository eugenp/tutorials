package com.baeldung.serenity;

import com.baeldung.serenity.screenplay.GoogleSearchResults;
import com.baeldung.serenity.screenplay.SearchForKeyword;
import com.baeldung.serenity.screenplay.StartWith;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.abilities.BrowseTheWeb;
import net.thucydides.core.annotations.Managed;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import static net.serenitybdd.screenplay.GivenWhenThen.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.hasItem;

/**
 * Unit test for simple App.
 */
@RunWith(SerenityRunner.class)
public class GoogleSearchScreenplayLiveTest {

    @Managed(driver = "chrome") WebDriver browser;

    Actor kitty = Actor.named("kitty");

    @Before
    public void setup() {
        kitty.can(BrowseTheWeb.with(browser));
    }

    @Test
    public void whenGoogleBaeldungThenShouldSeeEugen() {
        givenThat(kitty).wasAbleTo(StartWith.googleSearchPage());

        when(kitty).attemptsTo(SearchForKeyword.of("baeldung"));

        then(kitty).should(seeThat(GoogleSearchResults.displayed(), hasItem(containsString("Eugen (Baeldung)"))));
    }

}
