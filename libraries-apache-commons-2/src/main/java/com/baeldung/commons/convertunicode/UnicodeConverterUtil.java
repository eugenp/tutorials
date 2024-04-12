package com.baeldung.commons.convertunicode;

import org.apache.commons.text.StringEscapeUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnicodeConverterUtil {

    public static String decodeWithApacheCommons(String input) {
        return StringEscapeUtils.unescapeJava(input);
    }

    public static String decodeWithPlainJava(String input) {
        Pattern pattern = Pattern.compile("\\\\u[0-9a-fA-F]{4}");
        Matcher matcher = pattern.matcher(input);

        StringBuilder decodedString = new StringBuilder();

        while (matcher.find()) {
            String unicodeSequence = matcher.group();
            char unicodeChar = (char) Integer.parseInt(unicodeSequence.substring(2), 16);
            matcher.appendReplacement(decodedString, Character.toString(unicodeChar));
        }

        matcher.appendTail(decodedString);
        return decodedString.toString();
    }
}
