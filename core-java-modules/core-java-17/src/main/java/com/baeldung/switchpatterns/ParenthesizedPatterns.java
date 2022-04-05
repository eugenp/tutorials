package com.baeldung.switchpatterns;

public class ParenthesizedPatterns {

    static double getDoubleValueUsingIf(Object o) {
        return switch (o) {
            case String s -> {
                if (s.length() > 0) {
                    if (s.contains("#") || s.contains("@")) {
                        yield 0d;
                    } else {
                        yield Double.parseDouble(s);
                    }
                } else {
                    yield 0d;
                }
            }
            default -> 0d;
        };
    }

    static double getDoubleValueUsingParenthesizedPatterns(Object o) {
        return switch (o) {
            case String s && s.length() > 0 && !(s.contains("#") || s.contains("@")) -> Double.parseDouble(s);
            default -> 0d;
        };
    }

}
