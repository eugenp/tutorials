package com.baeldung.file.separator;

import java.io.File;

public class FilePathSeparator {
    public static String getFilePathSeparator() {
        return File.pathSeparator;
    }

    public static char getFilePathSeparatorChar() {
        return File.pathSeparatorChar;
    }

    public static String getSystemProperty() {
        return System.getProperty("path.separator");
    }

    public static String buildFilePath(String[] pathNames) {
        String pathNameStr = "";
        if (pathNames == null || pathNames.length == 0) {
            return pathNameStr;
        }
        String completePath = String.join(File.pathSeparator, pathNames);
        return completePath;
    }
}
