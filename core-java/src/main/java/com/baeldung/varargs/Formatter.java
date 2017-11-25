package com.baeldung.varargs;

public class Formatter {

    private final static String FORMAT = "%s %s %s";

    public static void main(String... args) {
        Formatter formatter = new Formatter();

        System.out.println("Format with no arg: " + formatter.format());
        System.out.println("Format with  1 arg : " + formatter.format("foo"));
        System.out.println("Format with  2 args: " + formatter.format("foo", "foo"));
        System.out.println("Format with  3 args, using varargs: " + formatter.formatWithVarArgs("foo", "foo", "foo", "foo"));
        System.out.println("Format with no args, using varargs: " + formatter.formatWithVarArgs());

    }

    public String format() {
        return format("empty", "");
    }

    public String format(String value) {
        return format(value, "");
    }

    public String format(String val1, String val2) {
        return String.format(FORMAT, val1, val2, "");
    }

    public String formatWithVarArgs(String... values) {
        if (values.length == 0) {
            return "no arguments given";
        }

        return String.format(FORMAT, values);
    }

}
