package com.baeldung.insertnewlineinstringbuilder;

public class StringBuilderHelper {

    private StringBuilder sb;

    public StringBuilderHelper() {
        sb = new StringBuilder();
    }

    public StringBuilderHelper append(Object obj) {
        sb.append(obj != null ? obj.toString() : "");
        return this;
    }

    public StringBuilderHelper appendLineSeparator() {
        sb.append(System.lineSeparator());
        return this;
    }

    @Override
    public String toString() {
        return sb.toString();
    }
}