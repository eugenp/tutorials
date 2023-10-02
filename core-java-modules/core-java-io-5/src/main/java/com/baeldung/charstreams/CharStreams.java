package com.baeldung.charstreams;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CharStreams {

    public static void writingStringToFilePrintWriter(String fileName, String content) throws FileNotFoundException{
        File fileObj = new File(fileName);
        PrintWriter printWriter = new PrintWriter(fileObj);
        printWriter.print(content);
        printWriter.close();
    }

    public static void writingStringToFileFileWriter(String fileName, String content) throws IOException {
        File fileObj = new File(fileName);
        FileWriter fileWriter = new FileWriter(fileObj);
        fileWriter.write(content);
        fileWriter.close();

    }

    public static void writingNonStringToFilePrintWriter(String fileName, DailyTodo dailyTodo) throws IOException {
        File fileObj = new File(fileName);
        PrintWriter printWriter = new PrintWriter(fileObj, StandardCharsets.UTF_8);
        printWriter.print(dailyTodo);
        printWriter.close();
    }

    public static void writingNonStringToFileFileWriter(String fileName, DailyTodo dailyTodo) throws IOException {
        File fileObj = new File(fileName);
        FileWriter fileWriter = new FileWriter(fileObj);
        fileWriter.write(dailyTodo.toString());
        fileWriter.close();
    }

    public static void appendingToFilePrintWriter(String fileName, DailyTodo content) throws IOException {
        String existingText = "existing text";
        Files.write(Paths.get(fileName), existingText.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);
        PrintWriter printWriter = new PrintWriter(new File(fileName), StandardCharsets.UTF_8);
        printWriter.append(content.toString());
        printWriter.close();
    }

    public static void appendingToFileFileWriter(String fileName, DailyTodo content) throws IOException {
        String existingText = "some text";
        Files.write(Paths.get(fileName), existingText.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE_NEW);
        FileWriter fileWriter = new FileWriter(new File(fileName), true);
        fileWriter.write(content.toString());
        fileWriter.close();
    }

    public static void autoFlushPrintWriter(String fileName, DailyTodo content) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(new FileOutputStream(fileName), true);
        printWriter.println(content);
        printWriter.close();
    }

    public static boolean printWriterErrorFlag(String fileName, DailyTodo content) throws IOException {
        File fileObj = new File(fileName);
        PrintWriter printWriter = new PrintWriter(fileObj, StandardCharsets.UTF_8);
        printWriter.print(content);
        boolean result = printWriter.checkError();
        printWriter.close();
        return result;
    }

}
