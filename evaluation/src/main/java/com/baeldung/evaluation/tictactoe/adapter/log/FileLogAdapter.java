package com.baeldung.evaluation.tictactoe.adapter.log;

import com.baeldung.evaluation.tictactoe.port.ILog;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileLogAdapter implements ILog {

    String logFilePath;

    public FileLogAdapter(String logFilePath) {
        this.logFilePath = logFilePath;
    }

    @Override public void saveGame(String game) {
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            PrintWriter pw = new PrintWriter(new FileOutputStream(new File(logFilePath), true));
            pw.append(dateFormat.format(new Date()) + " - " + game + System.lineSeparator());
            pw.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
