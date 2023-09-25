package com.baeldung.charstreams;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

public class CharStreams {

    public static File writingStringToFilePrintWriter(String fileName, String content) throws FileNotFoundException{
        File fileObj = new File(fileName);
        PrintWriter printWriter = new PrintWriter(fileObj);
        printWriter.print(content);
        printWriter.flush();
        printWriter.close();

        return fileObj;
    }

    public static File writingStringToFileFileWriter(String fileName, String content) throws IOException {
        File fileObj = new File(fileName);
        FileWriter fileWriter = new FileWriter(fileObj);
        fileWriter.write(content);
        fileWriter.flush();
        fileWriter.close();

        return fileObj;

    }

    public static File writingNonStringToFilePrintWriter(String fileName, DailyTodo dailyTodo) throws FileNotFoundException, UnsupportedEncodingException {
        File fileObj = new File(fileName);
        PrintWriter printWriter = new PrintWriter(fileObj, "utf-8");
        printWriter.print(dailyTodo);
        printWriter.flush();
        printWriter.close();

        return fileObj;

    }

    public static File writingNonStringToFileFileWriter(String fileName, DailyTodo dailyTodo) throws IOException {
        File fileObj = new File(fileName);
        FileWriter fileWriter = new FileWriter(fileObj);
        fileWriter.write(dailyTodo.toString());
        //fileWriter.flush();
        fileWriter.close();

        return fileObj;

    }

    public static File appendingToFilePrintWriter(String fileName, DailyTodo content) throws FileNotFoundException, UnsupportedEncodingException {
        File fileObj = new File(fileName);
        PrintWriter printWriter = new PrintWriter(fileObj, "utf-8");
        printWriter.append(content.toString());
        printWriter.flush();
        printWriter.close();

        return fileObj;
    }

    public static File appendingToFileFileWriter(String fileName, DailyTodo content) throws IOException {
        File fileObj = new File(fileName);
        FileWriter fileWriter = new FileWriter(fileObj, true);
        fileWriter.write(content.toString());
        fileWriter.flush();
        fileWriter.close();

        return fileObj;

    }

    public static void autoFlushPrintWriter(String fileName, DailyTodo content) throws FileNotFoundException {
        PrintWriter printWriter = new PrintWriter(new FileOutputStream(fileName), true);
        printWriter.println(content);
        printWriter.close();
    }


}
