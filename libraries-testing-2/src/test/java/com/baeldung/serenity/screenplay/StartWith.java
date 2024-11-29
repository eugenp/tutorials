package com.baeldung.serenity.screenplay;

import static net.serenitybdd.screenplay.Tasks.instrumented;

import net.serenitybdd.screenplay.Actor;
import net.serenitybdd.screenplay.Task;
import net.serenitybdd.screenplay.actions.Open;
import net.thucydides.core.annotations.Step;

public class StartWith implements Task {

    public static StartWith googleSearchPage() {
        return instrumented(StartWith.class);
    }

    private GoogleSearchPage googleSearchPage;

    @Step("{0} starts a google search")
    public <T extends Actor> void performAs(T t) {
        t.attemptsTo(Open.browserOn().the(googleSearchPage));
    }

}
