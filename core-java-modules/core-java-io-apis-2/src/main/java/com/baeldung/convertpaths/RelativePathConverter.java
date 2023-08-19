package com.baeldung.convertpaths;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;

public class RelativePathConverter {

    public static String convertToAbsoluteUsePathsClass(String relativePath) {
        Path absolutePath = Paths.get(relativePath).toAbsolutePath();
        return absolutePath.toString();
    }

    public static String convertToAbsoluteUseFileClass(String relativePath) {
        File file = new File(relativePath);
        return file.getAbsolutePath();
    }

    public static String convertToAbsoluteUseFileSystemsClass(String relativePath) {
        Path absolutePath = FileSystems.getDefault().getPath(relativePath).toAbsolutePath();
        return absolutePath.toString();
    }

    public static void main(String[] args) {
        String relativePath = "myFolder/myFile.txt";
        String absolutePath = convertToAbsoluteUseFileSystemsClass(relativePath);
        System.out.println("Absolute Path: " + absolutePath);
    }
}
