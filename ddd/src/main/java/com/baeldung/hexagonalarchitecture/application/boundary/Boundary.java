package com.baeldung.hexagonalarchitecture.application.boundary;

import com.baeldung.hexagonalarchitecture.application.DisplayRandomGreeting;
import com.baeldung.hexagonalarchitecture.application.boundary.drivenports.IObtainGreetings;
import com.baeldung.hexagonalarchitecture.application.boundary.drivenports.IPrintGreetings;
import com.baeldung.hexagonalarchitecture.application.boundary.driverports.ICommandOperator;
import com.baeldung.hexagonalarchitecture.domain.command.RequestGreeting;

public class Boundary implements ICommandOperator {
        private IObtainGreetings greetingsObtainer;
        private IPrintGreetings greetingsPrinter;

        public Boundary(IObtainGreetings greetingsObtainer, IPrintGreetings greetingsPrinter) {
                this.greetingsObtainer = greetingsObtainer;
                this.greetingsPrinter = greetingsPrinter;
        }

        @Override public void reactTo(Object commandObject) {
                RequestGreeting requestGreeting = (RequestGreeting) commandObject;
                DisplayRandomGreeting displayRandomGreeting = new DisplayRandomGreeting(greetingsObtainer, greetingsPrinter);
                displayRandomGreeting.displayGreeting(requestGreeting);
        }
}
