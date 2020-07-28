package com.baeldung.copydirectory;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class ApacheCommons {

    public static void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation) throws IOException {
        File sourceDirectory = new File(sourceDirectoryLocation);
        File destinationDirectory = new File(destinationDirectoryLocation);
        FileUtils.copyDirectory(sourceDirectory, destinationDirectory);
    }
}
