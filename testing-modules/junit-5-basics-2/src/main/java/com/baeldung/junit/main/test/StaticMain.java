package com.baeldung.junit.main.test;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Scanner;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class StaticMain {

    public static void main(String[] args) {
        System.out.println("Received input parameters: " + Arrays.asList(args));

        processRequest(args);
    }

    public static void processRequest(String[] args) {
        try {
            Options options = getOptions();
            CommandLineParser parser = new DefaultParser();
            CommandLine commandLine = parser.parse(options, args);

            if (commandLine.hasOption("V")) {
                System.out.print("Option V is present. The value is: ");
                System.out.println(commandLine.getOptionValue("V"));
            }

            if (commandLine.hasOption("i")) {
                System.out.print("Option i is present. The value is: " + commandLine.getOptionValue("i") + " \n");
                String optionValue = commandLine.getOptionValue("i");
                InputType inputType = InputType.valueOf(optionValue);

                String fileName = null;
                if (commandLine.hasOption("f")) {
                    fileName = commandLine.getOptionValue("f");
                }
                String inputString = read(inputType, fileName);
                int calculatedSum = calculateSum(inputString);
            }

        } catch (ParseException exception) {
            System.out.print("Parse error: ");
            System.out.println(exception.getMessage());
        }
    }

    public static Options getOptions() {
        Option inputTypeOption = Option.builder("i")
            .longOpt("input")
            .required(true)
            .desc("The input type")
            .type(InputType.class)
            .hasArg()
            .build();
        Option fileNameOption = Option.builder("f")
            .required(false)
            .desc("The file name option")
            .type(String.class)
            .hasArg()
            .build();

        Options options = new Options();
        options.addOption(inputTypeOption);
        options.addOption(fileNameOption);
        return options;
    }

    public static int calculateSum(String input) {
        if (input == null) {
            return 0;
        }
        String[] array = input.split(" ");
        int sum = Arrays.stream(array)
            .map(Integer::valueOf)
            .mapToInt(Integer::intValue)
            .sum();
        System.out.println("Calculated sum: " + sum);
        return sum;
    }

    private static String readFromConsole() {
        System.out.println("Enter values for calculation: \n");
        return new Scanner(System.in).nextLine();
    }

    private static String readFromFile(String fileName) {
        String readString = null;
        try {
            readString = Files.readString(Path.of(URI.create(fileName)));
            System.out.println(readString);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static String read(InputType inputType, String fileName) {
        switch (inputType) {
        case FILE:
            return readFromFile(fileName);
        case CONSOLE:
            return readFromConsole();
        default:
            return null;
        }
    }
}