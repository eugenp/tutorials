package com.baeldung.insertnewlineinstringbuilder;

public class NewlineUsingLineSeparator {
    public static String getFormattedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("First Line");
        sb.append(System.lineSeparator());
        sb.append("Second Line");
        return sb.toString();
    }
}