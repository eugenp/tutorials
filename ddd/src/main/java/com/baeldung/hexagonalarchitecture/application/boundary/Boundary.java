package com.baeldung.hexagonalarchitecture.application.boundary;

import com.baeldung.hexagonalarchitecture.application.DisplayRandomGreeting;
import com.baeldung.hexagonalarchitecture.application.boundary.driverports.ICommandOperator;
import com.baeldung.hexagonalarchitecture.application.boundary.drivenports.IObtainGreetings;
import com.baeldung.hexagonalarchitecture.application.boundary.drivenports.IPrintGreetings;
import com.baeldung.hexagonalarchitecture.domain.UseCaseModel;

import org.requirementsascode.Model;
import org.requirementsascode.ModelRunner;

public class Boundary implements ICommandOperator {
        private Model model;

        public Boundary(IObtainGreetings greetingsObtainer, IPrintGreetings greetingsPrinter) {
                model = buildModel(greetingsObtainer, greetingsPrinter);
        }

        private Model buildModel(IObtainGreetings greetingsObtainer, IPrintGreetings greetingsPrinter) {
                // Create the command handler(s)
                DisplayRandomGreeting displayRandomGreeting = new DisplayRandomGreeting(greetingsObtainer, greetingsPrinter);

                // Inject command handler(s) into use case model, to tie them to command
                // types.
                return UseCaseModel.build(displayRandomGreeting);

        }

        @Override public void reactTo(Object commandObject) {
                new ModelRunner().run(model).reactTo(commandObject);
        }
}
