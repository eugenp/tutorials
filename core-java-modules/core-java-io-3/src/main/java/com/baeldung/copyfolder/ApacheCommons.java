package com.baeldung.copyfolder;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class ApacheCommons {

    public static void copyDirectory(String sourceDirectoryLocation, String destinationDirectoryLocation) throws IOException {
        File sourceFolder = new File(sourceDirectoryLocation);
        File destinationFolder = new File(destinationDirectoryLocation);
        FileUtils.copyDirectory(sourceFolder, destinationFolder);
    }
}
