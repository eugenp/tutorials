package com.baeldung.hexagonalarchitecture.infrastructutre.drivenadapter;

import com.baeldung.hexagonalarchitecture.application.boundary.drivenports.IPrintGreetings;

public class GreetingsPrinterStub implements IPrintGreetings {
        private String[] greetings;

        @Override public void printGreetings(String[] greetings) {
                this.greetings = greetings;
        }

        public String[] getGreetings() {
                return greetings;
        }
}