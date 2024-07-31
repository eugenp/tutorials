package com.baeldung.stringbuilder;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.StringJoiner;

public class RemoveLastChar {
    static List<String> technologies = List.of("Spring Boot", "Quarkus", "Micronaut");

    public static StringBuilder joinStringsWithLastCharAsDelimiter() {
        StringBuilder sb = new StringBuilder();

        for (String tech : technologies) {
            sb.append(tech)
                .append(",");
        }

        return sb;
    }

    public static StringBuilder removeLastCharWithDeleteLastChar(StringBuilder sb) {

        if (sb.length() == 0) {
            return sb;
        }

        return sb.deleteCharAt(sb.length() - 1);
    }

    public static StringBuilder removeLastCharWithSetLength(StringBuilder sb) {

        if (sb.length() == 0) {
            return sb;
        }

        sb.setLength(sb.length() - 1);
        return sb;
    }

    public static StringBuilder joinStringsWithoutLastCharAsDelimiter() {
        StringBuilder sb = new StringBuilder();
        String prefix = "";

        for (String tech : technologies) {
            sb.append(prefix);
            prefix = ",";
            sb.append(tech);
        }

        return sb;
    }

    public static String joinStringsUsingJoin() {
        return String.join(",", technologies);
    }

    public static String joinUsingStringJoiner() {
        StringJoiner joiner = new StringJoiner(",");
        technologies.forEach(joiner::add);
        return joiner.toString();
    }

    public static String joinUsingStringUtils() {
        return StringUtils.join(technologies, ",");
    }
}
