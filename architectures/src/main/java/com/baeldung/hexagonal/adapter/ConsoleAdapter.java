package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.core.model.WeightInTime;
import com.baeldung.hexagonal.core.port.WeightTrackerAllService;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConsoleAdapter implements UIAdapter {
    LineReader scanner;
    PrintStream out;
    DataCollector dataCollector;
    State currentState = State.WELCOME;
    static final String YEAR_PATTERN = "(19[\\d{2}]|20\\d{2})"; // 1900 until 2099 inclusive
    static final String MONTH_PATTERN = "(0[1-9]|1[0-2])"; // 01 until 12 inclusive
    static final String DAY_PATTERN = "(0[1-9]|[12]\\d|3[01])"; // 01 until 31 inclusive (independent of the month)
    static final String HOUR_PATTERN = "([01]\\d|2[0-3])"; // 00 until 23
    static final String MIN_SEC_PATTERN = "[0-5]\\d"; // 00 until 59
    static final String TIME_PATTERN = String.format("( %s:%s:%s)?", HOUR_PATTERN, MIN_SEC_PATTERN, MIN_SEC_PATTERN);
    static final String DATETIME_VALIDATION_PATTERN = String.format("^%s-%s-%s%s$", YEAR_PATTERN, MONTH_PATTERN, DAY_PATTERN, TIME_PATTERN);

    public static ConsoleAdapter of(LineReader scanner, PrintStream out, WeightTrackerAllService editorService) {
        return new ConsoleAdapter(scanner, out, new DataCollector(editorService));
    }

    public static ConsoleAdapter of(WeightTrackerAllService editorService) {
        return new ConsoleAdapter(new LineReader(), System.out, new DataCollector(editorService));
    }

    @Override
    public void display() {
        while (currentState != State.EXIT) {
            this.currentState = currentState.collect(scanner, out, dataCollector);
        }
    }

    enum State {
        WELCOME {
            State collect(LineReader scanner, PrintStream out, DataCollector dataCollector) {
                out.println("Welcome to \"Weight Tracker\" - Console Adapter");
                out.println("==============================");
                out.println("Exit anytime by typing EXIT or QUIT.\n\n");

                return MENU;
            }
        },
        MENU {
            State collect(LineReader scanner, PrintStream out, DataCollector dataCollector) {
                out.println("Please select the option below:");
                out.println("1 - Enter Weight");
                out.println("2 - Show History");
                out.println("3 - Delete History Item");

                String line = scanner.nextLine();
                if (isEnd(line)) return END_SESSION;

                switch (line) {
                    case "1":
                        return COLLECTING_WEIGHT;
                    case "2":
                        return LISTING_HISTORY;
                    case "3":
                        return DELETE_HISTORY_ITEM;
                }

                out.println("The option entered is invalid.");
                return MENU;
            }
        },
        COLLECTING_WEIGHT {
            State collect(LineReader scanner, PrintStream out, DataCollector dataCollector) {
                out.println("Please enter your weight");
                String line = scanner.nextLine();
                if (isEnd(line)) return END_SESSION;

                if (isPositiveFloat(line)) {
                    dataCollector.addWeight(Float.parseFloat(line));
                    return COLLECTING_DATE;
                } else {
                    out.println("The weight entered is invalid.");
                    return COLLECTING_WEIGHT;
                }
            }
        },
        COLLECTING_DATE {
            State collect(LineReader scanner, PrintStream out, DataCollector dataCollector) {
                out.printf("When did you take your weight? (Just hit enter for today) Format %s%n", FORMATTER_PATTERN);
                String line = scanner.nextLine();
                if (isEnd(line)) return END_SESSION;

                LocalDateTime date;
                if (line.isEmpty()) {
                    date = LocalDateTime.now();
                } else if (isDate(line)) {
                    date = parseDate(line);
                    if (date == null) {
                        out.println("The date inserted is invalid.");
                        return COLLECTING_DATE;
                    }
                } else {
                    out.println("The date inserted is invalid.");
                    return COLLECTING_DATE;
                }

                dataCollector.addDate(date);
                out.println(">> Weight Added.\n");
                return MENU;
            }
        },
        LISTING_HISTORY {
            State collect(LineReader scanner, PrintStream out, DataCollector dataCollector) {
                List<WeightInTime> history = dataCollector.getHistory();
                if (history.isEmpty()) {
                    out.println("No history found\n\n");

                    return MENU;
                }

                history.forEach(weightInTime -> out.printf(">> (%s) %s - %skg%n", weightInTime.getId(), formatDate(weightInTime.getWhen()), weightInTime.getWeight()));

                out.println("Press \"enter\" to go back to menu.");
                String line = scanner.nextLine();
                if (isEnd(line)) return END_SESSION;

                return MENU;
            }
        },
        DELETE_HISTORY_ITEM {
            State collect(LineReader scanner, PrintStream out, DataCollector dataCollector) {
                out.println("Type the id of the item to delete:");
                String line = scanner.nextLine();
                if (isEnd(line)) return END_SESSION;
                if (!isPositiveLong(line)) {
                    out.println("The id entered is invalid.");
                    return DELETE_HISTORY_ITEM;
                }

                if (dataCollector.service.remove(Long.parseLong(line))) {
                    out.println(">> Removed Successfully.");
                    return MENU;
                } else {
                    out.println("Unable to remove item.\nPlease try again:");
                    return DELETE_HISTORY_ITEM;
                }
            }
        },
        END_SESSION,
        EXIT;

        static final String FORMATTER_PATTERN = "yyyy-MM-dd HH:mm:ss";
        static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(FORMATTER_PATTERN);

        State collect(LineReader scanner, PrintStream out, DataCollector dataCollector) {
            out.println("Thanks for using our service.\nGood bye.");
            return EXIT;
        }

        String formatDate(LocalDateTime date) {
            return date.format(FORMATTER);
        }

        static LocalDateTime parseDate(String line) {
            line += line.length() == 10? " 00:00:00" : ""; // If user only gives the date part

            try {
                return LocalDateTime.parse(line, FORMATTER);
            } catch (DateTimeParseException ex) {
                return null;
            }
        }
    }

    ConsoleAdapter(LineReader scanner, PrintStream out, DataCollector dataCollector) {
        this.scanner = scanner;
        this.out = out;
        this.dataCollector = dataCollector;
    }

    static boolean isEnd(String line) {
        return "EXIT".equalsIgnoreCase(line.trim()) || "QUIT".equalsIgnoreCase(line.trim());
    }

    static boolean isPositiveFloat(String line) {
        return line.matches("^\\d+(\\.\\d+)?$");
    }

    static boolean isPositiveLong(String line) {
        return line.matches("^\\d+$");
    }

    static boolean isDate(String line) {
        return line.matches(DATETIME_VALIDATION_PATTERN);
    }

    static class DataCollector {
        WeightTrackerAllService service;
        float weight;

        DataCollector(WeightTrackerAllService service) {
            this.service = service;
        }

        void addWeight(float weight) {
            this.weight = weight;
        }

        void addDate(LocalDateTime localDate) {
            service.addWeight(weight, localDate);
        }

        List<WeightInTime> getHistory() {
            // Since the API does not tell if the history will be sorted, we sort here because that is how we want to display it
            return service.getWeightHistory().stream().sorted(Comparator.comparing(WeightInTime::getWhen)).collect(Collectors.toList());
        }
    }

    public static class LineReader {
        Scanner scanner = new Scanner(System.in);

        String nextLine() {
            return scanner.nextLine();
        }

        boolean hasNextLine() {
            return scanner.hasNextLine();
        }
    }
}
