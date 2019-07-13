package com.baeldung.core.pwd;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Paths;

public final class CurrentDirectoryFetcher {

    public static void main(String[] args) {
        System.out.println(currentDirectoryUsingSystemProperties());

        System.out.println(currentDirectoryUsingFileSystems());

        System.out.println(currentDirectoryUsingFile());

        System.out.println(currentDirectoryUsingPaths());
    }

    public static String currentDirectoryUsingSystemProperties() {
        return System.getProperty("user.dir");
    }

    public static String currentDirectoryUsingPaths() {
        return Paths.get("")
            .toAbsolutePath()
            .toString();
    }

    public static String currentDirectoryUsingFileSystems() {
        return FileSystems.getDefault()
            .getPath("")
            .toAbsolutePath()
            .toString();
    }

    public static String currentDirectoryUsingFile() {
        return new File("").getAbsolutePath();
    }

}
