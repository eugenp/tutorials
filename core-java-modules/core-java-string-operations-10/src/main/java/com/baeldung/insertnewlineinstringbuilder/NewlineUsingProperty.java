package com.baeldung.insertnewlineinstringbuilder;

public class NewlineUsingProperty {
    public static String getFormattedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("First Line");
        sb.append(System.getProperty("line.separator"));
        sb.append("Second Line");
        return sb.toString();
    }
}
