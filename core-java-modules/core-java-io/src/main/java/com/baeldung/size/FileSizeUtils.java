package com.baeldung.size;

import java.io.File;

public class FileSizeUtils {
    public static long getFileSizeInBytes(File file) {
        if (file.exists()) {
            return file.length();
        } else {
            throw new IllegalArgumentException("File not found.");
        }
    }

    public static double getFileSizeInKilobytes(File file) {
        long bytes = getFileSizeInBytes(file);
        return (double) bytes / 1024;
    }

    public static double getFileSizeInMegabytes(File file) {
        double kilobytes = getFileSizeInKilobytes(file);
        return kilobytes / 1024;
    }

    public static double getFileSizeInGigabytes(File file) {
        double megabytes = getFileSizeInMegabytes(file);
        return megabytes / 1024;
    }
}
