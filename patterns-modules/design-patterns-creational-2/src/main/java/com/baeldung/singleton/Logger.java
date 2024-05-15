package com.baeldung.singleton;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;

public class Logger {
    private static volatile Logger instance;

    private PrintWriter fileWriter;

    public static Logger getInstance() {
        if (instance == null) {
            synchronized (Logger.class) {
                if (instance == null) {
                    instance = new Logger();
                }
            }
        }
        return instance;
    }

    private Logger() {
        try {
            fileWriter = new PrintWriter(new FileWriter("app.log"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void log(String message) {
        String log = String.format("[%s]- %s", LocalDateTime.now(), message);
        fileWriter.println(log);
        fileWriter.flush();
    }

}