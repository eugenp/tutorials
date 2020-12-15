package com.baeldung.file.separator;

import java.io.File;
import java.util.StringJoiner;

public class FilePathSeparator {

    public static String buildPathUsingString(String[] pathNames) {
        return String.join(File.pathSeparator, pathNames);
    }

    public static String buildPathUsingStringJoiner(String path1, String path2) {
        StringJoiner joiner = new StringJoiner(File.pathSeparator);
        joiner.add(path1);
        joiner.add(path2);
        return joiner.toString();
    }
}
