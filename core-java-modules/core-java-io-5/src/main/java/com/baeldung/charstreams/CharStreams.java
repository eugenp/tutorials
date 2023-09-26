package com.baeldung.charstreams;

import java.io.*;
public class CharStreams {

    public static void writingStringToFilePrintWriter(String fileName, String content) throws FileNotFoundException{
        PrintWriter printWriter = new PrintWriter(fileName);
        printWriter.print(content);
        printWriter.flush();
        printWriter.close();
    }

    public static void writingStringToFileFileWriter(String fileName, String content) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();

    }

    public static void writingNonStringToFilePrintWriter(String fileName, DailyTodo dailyTodo) throws FileNotFoundException, UnsupportedEncodingException {
        PrintWriter printWriter = new PrintWriter(fileName, "utf-8");
        printWriter.print(dailyTodo);
        printWriter.flush();
        printWriter.close();
    }

    public static void writingNonStringToFileFileWriter(String fileName, DailyTodo dailyTodo) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName);
        fileWriter.write(dailyTodo.toString());
        fileWriter.flush();
        fileWriter.close();
    }

    public static void appendingToFilePrintWriter(String fileName, DailyTodo content) throws FileNotFoundException, UnsupportedEncodingException {
        File fileObj = new File(fileName);
        PrintWriter printWriter = new PrintWriter(fileName, "utf-8");
        printWriter.append(content.toString());
        printWriter.flush();
        printWriter.close();
    }

    public static void appendingToFileFileWriter(String fileName, DailyTodo content) throws IOException {
        FileWriter fileWriter = new FileWriter(fileName, true);
        fileWriter.write(content.toString());
        fileWriter.flush();
        fileWriter.close();
    }

    public static void autoFlushPrintWriter(String fileName, DailyTodo content) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(new FileOutputStream(fileName), true);
        printWriter.println(content);
        printWriter.close();
    }

}
