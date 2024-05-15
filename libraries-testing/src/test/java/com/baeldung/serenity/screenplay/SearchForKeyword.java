package com.baeldung.serenity.screenplay;

import net.serenitybdd.core.steps.Instrumented;
import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Enter;
import net.thucydides.core.annotations.Step;
import org.openqa.selenium.Keys;

public class SearchForKeyword implements Task {

    @Step("{0} searches for '#keyword'")
    public <T extends Actor> void performAs(T actor) {
        actor.attemptsTo(Enter.theValue(keyword).into(GoogleSearchPage.SEARCH_INPUT_BOX).thenHit(Keys.RETURN));
    }

    private String keyword;

    public SearchForKeyword(String keyword) {
        this.keyword = keyword;
    }

    public static Task of(String keyword) {
        return Instrumented.instanceOf(SearchForKeyword.class).withProperties(keyword);
    }

}
