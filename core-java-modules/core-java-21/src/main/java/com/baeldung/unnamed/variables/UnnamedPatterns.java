package com.baeldung.unnamed.variables;

public class UnnamedPatterns {

    static String getObjectsColorWithNamedPattern(Object object) {
        if (object instanceof Car(String name, String color, Engine engine)) {
            return color;
        }
        return "No color!";
    }

    static String getObjectsColorWithUnnamedPattern(Object object) {
        if (object instanceof Car(_, String color, _)) {
            return color;
        }
        return "No color!";
    }

    static String getObjectsColorWithSwitchAndNamedPattern(Object object) {
        return switch (object) {
            case Car(String name, String color, Engine engine) -> color;
            default -> "No color!";
        };
    }

    static String getObjectsColorWithSwitchAndUnnamedPattern(Object object) {
        return switch (object) {
            case Car(_, String color, _) -> color;
            default -> "No color!";
        };
    }

    static String getEngineTypeWithNamedPattern(Car<?> car) {
        return switch (car) {
            case Car(String name, String color, GasEngine engine) -> "gas";
            case Car(String name, String color, ElectricEngine engine) -> "electric";
            case Car(String name, String color, HybridEngine engine) -> "hybrid";
            default -> "none";
        };
    }

    static String getEngineTypeWithUnnamedPattern(Car<?> car) {
        return switch (car) {
            case Car(_, _, GasEngine _) -> "gas";
            case Car(_, _, ElectricEngine _) -> "electric";
            case Car(_, _, HybridEngine _) -> "hybrid";
            default -> "none";
        };
    }
}
