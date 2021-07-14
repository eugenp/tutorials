package com.baeldung.multiline;

import com.google.common.base.Joiner;
import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MultiLineString {
    
    String newLine = System.getProperty("line.separator");
    
    public String stringConcatenation() {
        return "Get busy living"
                .concat(newLine)
                .concat("or")
                .concat(newLine)
                .concat("get busy dying.")
                .concat(newLine)
                .concat("--Stephen King");
    }
    
    public String stringJoin() {
        return String.join(newLine,
                           "Get busy living",
                           "or",
                           "get busy dying.",
                           "--Stephen King");
    }
    
    public String stringBuilder() {
        return new StringBuilder()
                .append("Get busy living")
                .append(newLine)
                .append("or")
                .append(newLine)
                .append("get busy dying.")
                .append(newLine)
                .append("--Stephen King")
                .toString();
    }
    
    public String stringWriter() {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        printWriter.println("Get busy living");
        printWriter.println("or");
        printWriter.println("get busy dying.");
        printWriter.println("--Stephen King");
        return stringWriter.toString();
    }
    
    public String guavaJoiner() {
        return Joiner.on(newLine).join(ImmutableList.of("Get busy living",
            "or",
            "get busy dying.",
            "--Stephen King"));
    }
    
    public String loadFromFile() throws IOException {
        return new String(Files.readAllBytes(Paths.get("src/main/resources/stephenking.txt")));
    }

    public String textBlocks() {
        return """
            Get busy living
            or
            get busy dying.
            --Stephen King""";
    }
}
