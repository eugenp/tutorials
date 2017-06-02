package com.baeldung.demo;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * Created by elyor on 27.05.2017.
 */
public class SimpleDirectorySynchronizer implements Runnable {
    private String sourceDirectory;
    private String destinationDirectory;

    public SimpleDirectorySynchronizer() {
    }

    public SimpleDirectorySynchronizer(String sourceDirectory, String destinationDirectory) {
        this.sourceDirectory = sourceDirectory;
        this.destinationDirectory = destinationDirectory;
    }

    public void setSourceDirectory(String sourceDirectory) {
        this.sourceDirectory = sourceDirectory;
    }

    public void setDestinationDirectory(String destinationDirectory) {
        this.destinationDirectory = destinationDirectory;
    }

    // standard getters

    public void run() {
        File sourceDir = new File(sourceDirectory);
        File destDir = new File(sourceDirectory);

        File[] filesToCopy = sourceDir.listFiles();
        for (File file : filesToCopy) {
            try {
                Files.copy(file.toPath(), destDir.toPath());
            } catch (IOException e) {
                System.out.println("Couldn't copy " + file.getName()
                  + " from " + sourceDir.getAbsolutePath()
                  + " to " + destDir.getAbsolutePath());
            }
        }
    }
}
