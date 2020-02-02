package com.baeldung.hexagonalarchitecture.application;

import com.baeldung.hexagonalarchitecture.application.boundary.drivenports.IObtainGreetings;
import com.baeldung.hexagonalarchitecture.application.boundary.drivenports.IPrintGreetings;
import com.baeldung.hexagonalarchitecture.domain.Greeting;
import com.baeldung.hexagonalarchitecture.domain.command.RequestGreeting;
import com.baeldung.hexagonalarchitecture.domain.helper.RandomGreetingPicker;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class DisplayRandomGreeting implements Consumer<RequestGreeting> {

        private IObtainGreetings greetingsObtainer;
        private RandomGreetingPicker greetingPicker;
        private IPrintGreetings greetingsPrinter;

        public DisplayRandomGreeting(IObtainGreetings greetingsObtainer, IPrintGreetings greetingsPrinter) {
                this.greetingsObtainer = greetingsObtainer;
                this.greetingPicker = new RandomGreetingPicker();
                this.greetingsPrinter = greetingsPrinter;
        }

        @Override public void accept(RequestGreeting requestGreeting) {
                List<Greeting> greetingList = obtainGreetings(requestGreeting);
                Optional<Greeting> greeting = greetingPicker.pickGreeting(greetingList);
                printLines(greeting);
        }

        private List<Greeting> obtainGreetings(RequestGreeting requestGreeting) {
                String language = requestGreeting.getLanguage();
                String[] greetings = greetingsObtainer.getGreetingsForLanguage(language);
                return Arrays.stream(greetings).map(Greeting::new).collect(Collectors.toList());

        }

        private void printLines(Optional<Greeting> greeting) {
                greeting.ifPresent(greeting1 -> greetingsPrinter.printGreetings(greeting1.getGreetings()));
        }
}
