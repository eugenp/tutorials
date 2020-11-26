package com.baeldung.file.separator;

import java.io.File;

public class FileSeparator {

    public static String getFileSeparator() {
        return File.separator;
    }

    public static char getFileSeparatorChar() {
        return File.separatorChar;
    }

    public static String getSystemProperty() {
        return System.getProperty("file.separator");
    }

    public static String buildFilePath(String[] fileNames) {
        String fileNameStr = "";
        if (fileNames == null || fileNames.length == 0) {
            return fileNameStr;
        }
        String filePath = String.join(File.separator, fileNames);
        return filePath;
    }
}
