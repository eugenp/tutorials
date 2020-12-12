package com.baeldung.file.separator;

import java.io.File;
import java.util.StringJoiner;

public class FilePathSeparator {

    public static String buildPathUsingString(String[] pathNames) {

        return String.join(File.pathSeparator, pathNames);
    }

    public static String buildPathUsingStringJoiner(String path4, String path5) {
        StringJoiner joiner = new StringJoiner(File.pathSeparator);
        joiner.add(path4);
        joiner.add(path5);
        return joiner.toString();
    }

}
