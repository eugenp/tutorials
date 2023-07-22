package com.baeldung.conditionals;

public class YieldExamples {

    public enum Number {
        ONE, TWO, THREE, FOUR;
    }

    public static String analyze(Number number) {
        String message;
        switch (number) {
            case ONE:
                message = "Got a 1";
                break;
            case TWO:
                message = "Got a 2";
                break;
            default:
                message = "More than 2";
        }
        return message;
    }

    public static String analyzeNumberArrow(Number number) {

        return switch (number) {
            case ONE -> {
                yield "Got a 1";
            }
            case TWO -> {
                yield "Got a 2";
            }
            default -> {
                yield "More than 2";
            }
        };
    }

    public static String analyzeNumberColon(Number number) {
        return switch (number) {
            case ONE:
                yield "Got a 1";
            case TWO:
                yield "Got a 2";
            default:
                yield "More than 2";
        };
    }
}