package com.baeldung.fj;

import fj.F;
import fj.F1Functions;
import fj.Unit;
import fj.data.IO;
import fj.data.IOFunctions;

public class FunctionalJavaIOMain {

    public static IO<Unit> printLetters(final String s) {
        return () -> {
            for (int i = 0; i < s.length(); i++) {
                System.out.println(s.charAt(i));
            }
            return Unit.unit();
        };
    }

    public static void main(String[] args) {

        F<String, IO<Unit>> printLetters = i -> printLetters(i);

        IO<Unit> lowerCase = IOFunctions.stdoutPrintln("What's your first Name ?");

        IO<Unit> input = IOFunctions.stdoutPrint("First Name: ");

        IO<Unit> userInput = IOFunctions.append(lowerCase, input);

        IO<String> readInput = IOFunctions.stdinReadLine();

        F<String, String> toUpperCase = i -> i.toUpperCase();

        F<String, IO<Unit>> transformInput = F1Functions.<String, IO<Unit>, String> o(printLetters).f(toUpperCase);

        IO<Unit> readAndPrintResult = IOFunctions.bind(readInput, transformInput);

        IO<Unit> program = IOFunctions.bind(userInput, nothing -> readAndPrintResult);

        IOFunctions.toSafe(program).run();

    }
}
