package com.baeldung.infra;

import com.baeldung.domain.Recipe;
import com.baeldung.domain.ports.ISmartRefrigerator;

import java.util.Optional;

public class ConsoleAdapter {
    private final ISmartRefrigerator smartRefrigerator;
    private final IConsoleWriter consoleWriter;

    public ConsoleAdapter(ISmartRefrigerator smartRefrigerator, IConsoleWriter consoleWriter) {
        this.smartRefrigerator = smartRefrigerator;
        this.consoleWriter = consoleWriter;
    }

    public ConsoleAdapter(ISmartRefrigerator smartRefrigerator) {
        this.smartRefrigerator = smartRefrigerator;
        this.consoleWriter = new DefaultConsoleWriter();
    }

    public void AskWhatRefrigeratorHas() {
        this.consoleWriter.writeLine(this.smartRefrigerator.whatIHave());
        this.consoleWriter.writeLine("----------------");
    }

    public void AskWhichRecipeRefrigeratorOffers() {
        Optional<Recipe> optionalRecipe = this.smartRefrigerator.offerMeARecipe();
        if (optionalRecipe.isPresent()) {
            this.consoleWriter.writeLine(optionalRecipe.get().toString());
        } else {
            this.consoleWriter.writeLine("Nothing my friend. Buy some foods :)");
        }
    }

    private class DefaultConsoleWriter implements IConsoleWriter {
        @Override
        public void writeLine(String text) {
            System.out.println(text);
        }
    }
}
