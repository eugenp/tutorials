package com.baeldung.insertnewlineinstringbuilder;

public class NewlineUsingN {
    public static String getFormattedString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Line 1");
        sb.append("\n");
        sb.append("Line 2");
        return sb.toString();
    }
}

