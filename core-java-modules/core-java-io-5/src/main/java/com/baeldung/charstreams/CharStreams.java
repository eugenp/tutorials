package com.baeldung.charstreams;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CharStreams {

    public static File writingStringToFilePrintWriter(String fileName, String content) throws FileNotFoundException{
        File fileObj = new File(fileName);
        PrintWriter printWriter = new PrintWriter(fileName);
        printWriter.print(content);
        printWriter.close();
        return fileObj;
    }

    public static File writingStringToFileFileWriter(String fileName, String content) throws IOException {
        File fileObj = new File(fileName);
        FileWriter fileWriter = new FileWriter(fileObj);
        fileWriter.write(content);
        fileWriter.close();
        return fileObj;

    }

    public static File writingNonStringToFilePrintWriter(String fileName, DailyTodo dailyTodo) throws IOException {
        File fileObj = new File(fileName);
        PrintWriter printWriter = new PrintWriter(fileObj, StandardCharsets.UTF_8);
        printWriter.print(dailyTodo);
        printWriter.close();
        return fileObj;

    }

    public static File writingNonStringToFileFileWriter(String fileName, DailyTodo dailyTodo) throws IOException {
        File fileObj = new File(fileName);
        FileWriter fileWriter = new FileWriter(fileObj);
        fileWriter.write(dailyTodo.toString());
        fileWriter.close();
        return fileObj;
    }

    public static File appendingToFilePrintWriter(String fileName, DailyTodo content) throws IOException {
        File fileObj = new File(fileName);
        PrintWriter printWriter = new PrintWriter(fileObj, StandardCharsets.UTF_8);
        printWriter.append(content.toString());
        printWriter.close();
        return fileObj;
    }

    public static File appendingToFileFileWriter(String fileName, DailyTodo content) throws IOException {
        File fileObj = new File(fileName);
        FileWriter fileWriter = new FileWriter(fileObj, true);
        fileWriter.write(content.toString());
        fileWriter.close();
        return fileObj;

    }

    public static void autoFlushPrintWriter(String fileName, DailyTodo content) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(new FileOutputStream(fileName), true);
        printWriter.println(content);
        printWriter.close();
    }

    public static boolean checkErrorPrintWriter(String fileName, DailyTodo content) throws IOException {
        File fileObj = new File(fileName);
        PrintWriter printWriter = new PrintWriter(fileObj, StandardCharsets.UTF_8);
        printWriter.print(content);
        printWriter.close();
        return printWriter.checkError();
    }

}
