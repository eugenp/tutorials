package com.baeldung.junit.main.test;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class Bootstrapper {
    private final InputReader inputReader;
    private final Calculator calculator;

    public Bootstrapper(InputReader inputReader, Calculator calculator) {
        this.inputReader = inputReader;
        this.calculator = calculator;
    }

    public void processRequest(String[] args) {
        try {
            Options options = getOptions();
            CommandLineParser parser = new DefaultParser();
            CommandLine commandLine = parser.parse(options, args);

            if (commandLine.hasOption("i")) {
                System.out.print("Option i is present. The value is: " + commandLine.getOptionValue("i") + " \n");
                String optionValue = commandLine.getOptionValue("i");
                InputType inputType = InputType.valueOf(optionValue);

                String fileName = null;
                if (commandLine.hasOption("f")) {
                    fileName = commandLine.getOptionValue("f");
                }
                String inputString = inputReader.read(inputType, fileName);
                int calculatedSum = calculator.calculateSum(inputString);
            }

        } catch (ParseException exception) {
            System.out.print("Parse error: " + exception.getMessage());
        }
    }

    public static Options getOptions() {
        Option fileNameOption = Option.builder("f")
            .required(false)
            .desc("The file name option")
            .type(String.class)
            .build();
        Option inputTypeOption = Option.builder("i")
            .longOpt("input")
            .required(true)
            .desc("The input type")
            .type(InputType.class)
            .hasArg()
            .build();

        Options options = new Options();
        options.addOption(inputTypeOption);
        options.addOption(fileNameOption);
        return options;
    }
}