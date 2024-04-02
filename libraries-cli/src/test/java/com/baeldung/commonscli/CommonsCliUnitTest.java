package com.baeldung.commonscli;

import org.apache.commons.cli.AlreadySelectedException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.MissingArgumentException;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionGroup;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import static org.junit.jupiter.api.Assertions.*;

import org.apache.commons.cli.UnrecognizedOptionException;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonsCliUnitTest {
    Logger logger = LoggerFactory.getLogger(CommonsCliUnitTest.class);

    @Test
    void whenMutuallyExclusiveOptionsProvidedTogether_thenThrowAlreadySelectedException() {
        Option interactiveOption = new Option("i", false, "Prompts the user before overwriting the existing files");
        Option forceOption = new Option("f", false, "Overwrites the existing files without prompting");

        OptionGroup optionGroup = new OptionGroup();
        optionGroup.addOption(interactiveOption)
            .addOption(forceOption);

        Options options = new Options();
        options.addOptionGroup(optionGroup);

        String[] commandWithConflictingOptions = new String[]{"cp", "-i", "-f", "file1", "file2"};
        CommandLineParser commandLineParser = new DefaultParser();
        assertThrows(AlreadySelectedException.class, () -> {
            try {
                CommandLine commandLine = commandLineParser.parse(options, commandWithConflictingOptions);
            } catch (ParseException e) {
                assertTrue(e instanceof AlreadySelectedException);
                handleException(new RuntimeException(e));
                throw e;
            }
        });
    }

    @Test
    void whenMandatoryOptionMissing_thenThrowMissingOptionException() {
        Options options = createOptions();
        String[] commandWithMissingMandatoryOption = new String[]{"-h", "PGSERVER", "-U", "postgres"};
        CommandLineParser commandLineParser = new DefaultParser();
        assertThrows(MissingOptionException.class, () -> {
            try {
                CommandLine commandLine = commandLineParser.parse(options, commandWithMissingMandatoryOption);
            } catch (ParseException e) {
                assertTrue(e instanceof MissingOptionException);
                handleException(new RuntimeException(e));
                throw e;
            }
        });
    }

    @Test
    void whenOptionArgumentIsMissing_thenThrowMissingArgumentException() {
        Options options = createOptions();
        String[] commandWithOptionArgumentOption = new String[]{"-h", "PGSERVER", "-U", "postgres", "-d"};
        CommandLineParser commandLineParser = new DefaultParser();
        assertThrowsExactly(MissingArgumentException.class, () -> {
            try {
                CommandLine commandLine = commandLineParser.parse(options, commandWithOptionArgumentOption);
            } catch (ParseException e) {
                assertTrue(e instanceof MissingArgumentException);
                handleException(new RuntimeException(e));
                throw e;
            }
        });
    }

    @Test
    void whenUnrecognizedOptionProvided_thenThrowUnrecognizedOptionException() {
        Options options = createOptions();
        String[] commandWithIncorrectOption = new String[]{"-h", "PGSERVER", "-U", "postgres", "-d", "empDB", "-y"};
        CommandLineParser commandLineParser = new DefaultParser();
        assertThrows(UnrecognizedOptionException.class, () -> {
            try {
                CommandLine commandLine = commandLineParser.parse(options, commandWithIncorrectOption);
            } catch (ParseException e) {
                assertTrue(e instanceof UnrecognizedOptionException);
                handleException(new RuntimeException(e));
                throw e;
            }
        });
    }

    private void handleException(RuntimeException e) {
        logger.error("handle exception:" + e.getMessage());
    }

    @Test
    void whenNeedHelp_thenPrintHelp() {
        HelpFormatter helpFormatter = new HelpFormatter();
        Options options = createOptions();
        options.addOption("?", "help", false, "Display help information");
        helpFormatter.printHelp("psql -U username -h host -d empDB", options);
    }

    @Test
    void whenCliOptionProvided_thenParseAndExtractOptionAndArgumentValues() throws ParseException {

        Options options = new Options();

        Option hostOption = createOption("h", "host", "HOST","Database server host", false);
        Option userNameOption = createOption("U", "username", "USERNAME", "Database user name", true);
        Option dbNameOption = createOption("d", "dbName", "DBNAME", "Database name to connect to", true);

        options.addOption(hostOption)
            .addOption(dbNameOption)
            .addOption(userNameOption);

        String[] commandWithShortNameOptions = new String[] { "-h", "PGSERVER", "-U", "postgres", "-d", "empDB" };
        parseThenProcessCommand(options, commandWithShortNameOptions, "h", "U", "d" );

        String[] commandWithLongNameOptions =  new String[] { "--username", "postgres", "--dbName", "empDB" };
        parseThenProcessCommand(options, commandWithLongNameOptions, "host", "username", "dbName" );
    }

    private Options createOptions() {
        Options options = new Options();

        Option hostOption = createOption("h", "host", "HOST", "Database server host", true);
        Option userNameOption = createOption("U", "username", "USERNAME", "Database user name", true);
        Option dbNameOption = createOption("d", "dbName", "DBNAME", "Database name to connect to", true);

        return options.addOption(hostOption)
            .addOption(dbNameOption)
            .addOption(userNameOption);
    }

    private void parseThenProcessCommand(Options options, String[] commandArgs, String hostOption,
        String usernameOption, String dbNameOption) throws ParseException {
        CommandLineParser commandLineParser = new DefaultParser();

        CommandLine commandLine = commandLineParser.parse(options, commandArgs);
        String hostname = commandLine.hasOption("h") ? commandLine.getOptionValue(hostOption) : "localhost";
        String userName = commandLine.getOptionValue(usernameOption);
        String dbName = commandLine.getOptionValue(dbNameOption);

        if (commandLine.hasOption("h")) {
            assertEquals("PGSERVER", hostname);
        } else {
            assertEquals("localhost", hostname);
        }

        assertEquals("postgres", userName);
        assertEquals("empDB", dbName);
        createConnection(hostname, userName, dbName);
    }

    private void createConnection(String host, String userName, String dbName) {
        //call underlying service
    }

    private Option createOption(String shortName, String longName, String argName, String description, boolean required) {
        return Option.builder(shortName)
            .longOpt(longName)
            .argName(argName)
            .desc(description)
            .hasArg()
            .required(required)
            .build();
    }

}
