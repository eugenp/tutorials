package com.baeldung.insertnewlineinstringbuilder;

public class NewlineUsingFormat {
    public static String getFormattedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("First Line");
        sb.append(String.format("%n"));
        sb.append("Second Line");
        return sb.toString();
    }
}