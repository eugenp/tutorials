package com.baeldung.examples.slack;

import java.io.IOException;
import java.nio.file.FileSystems;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DiskSpaceErrorChecker implements ErrorChecker {
    private static final Logger LOG = LoggerFactory.getLogger(DiskSpaceErrorChecker.class);

    private final ErrorReporter errorReporter;

    private final double limit;

    public DiskSpaceErrorChecker(ErrorReporter errorReporter, double limit) {
        this.errorReporter = errorReporter;
        this.limit = limit;
    }

    @Override
    public void check() {
        LOG.info("Checking disk space");
        FileSystems.getDefault().getFileStores().forEach(fileStore -> {
            try {
                long totalSpace = fileStore.getTotalSpace();
                long usableSpace = fileStore.getUsableSpace();
                double usablePercentage = ((double) usableSpace) / totalSpace;
                LOG.debug("File store {} has {} of {} ({}) usable space",
                    fileStore, usableSpace, totalSpace, usablePercentage);

                if (totalSpace > 0 && usablePercentage < limit) {
                    String error = String.format("File store %s only has %d%% usable disk space",
                        fileStore.name(), (int)(usablePercentage * 100));
                    errorReporter.reportProblem(error);
                }
            } catch (IOException e) {
                LOG.error("Error getting disk space for file store {}", fileStore, e);
            }
        });
    }
}
