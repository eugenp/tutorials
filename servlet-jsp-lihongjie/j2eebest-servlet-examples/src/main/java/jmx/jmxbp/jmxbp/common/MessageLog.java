package jmxbp.common;

import  java.util.*;
import  java.text.*;
import  java.io.*;


/**
 * This is a utility class that writes messages to a log file that
 * has the format DDMMMYYYY.LOG, where MMM is the month. For example:
 * Mar is March, Apr is April, etc.
 */
public class MessageLog {
    // Keep objects that can be reused as instance variables
    /// to avoid unnecessarily creating objects
    private SimpleDateFormat _timestampFormatter = new SimpleDateFormat("EEE dd MMM yyyy HH:mm:ss.SSS");
    private SimpleDateFormat _filenameFormatter = new SimpleDateFormat("ddMMMyyyy");
    private DecimalFormat _decimalFormat = new DecimalFormat("00");
    private StringBuffer _filename = new StringBuffer();
    private StringBuffer _message = new StringBuffer();
    private boolean _includeThreadInfo;
    public static final boolean INCLUDE_THREAD_INFO = true;
    public static final boolean NO_INCLUDE_THREAD_INFO = false;

    public MessageLog () {
        this(NO_INCLUDE_THREAD_INFO);
    }

    public MessageLog (boolean includeThreadInfo) {
        _includeThreadInfo = includeThreadInfo;
    }

    public void write (String message) {
        Date now = new Date();
        String filename = createFileName(now);
        //
        // Open the file (append output) for output
        //
        try {
            FileWriter outputFile = new FileWriter(filename, true);
            String timestamp = createTimeStamp(now);
            _message.setLength(0);
            _message.append(timestamp);
            _message.append(": ");
            if (_includeThreadInfo) {
                _message.append(Thread.currentThread().toString());
                _message.append(": ");
            }
            _message.append(message);
            _message.append('\n');
            ;
            outputFile.write(_message.toString());
            outputFile.close();
        } catch (IOException e) {
            System.out.println("MessageLog.write(): ERROR: IO Exception occurred.");
            e.printStackTrace();
        }
    }
    public void write (Throwable t) {
        write("EXCEPTION: Stack trace follows...");
        try {
            Writer outputFile = new FileWriter(createFileName(new Date()), 
                    true);
            t.printStackTrace(new PrintWriter(outputFile));
            outputFile.flush();
            outputFile.close();
        } catch (IOException e) {
            System.out.println("MessageLog.write(): ERROR: IO Exception occurred.");
            e.printStackTrace();
        }
    }

    private String createFileName (Date now) {
        _filename.setLength(0);
        _filename.append(_filenameFormatter.format(now));
        _filename.append(".LOG");
        return  _filename.toString();
    }

    private String createTimeStamp (Date now) {
        return  _timestampFormatter.format(now);
    }
}


