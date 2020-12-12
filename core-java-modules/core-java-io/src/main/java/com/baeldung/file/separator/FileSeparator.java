package com.baeldung.file.separator;

import java.io.File;
import java.nio.file.Paths;

public class FileSeparator {

    public static String buildFilePathUsingPathsClass(String file1, String file2) {
        return Paths.get(file1, file2).toString();
    }

    public static String buildFilePathUsingFileClass(String file1, String file2) {
        return new File(file1, file2).toString();
    }

}
