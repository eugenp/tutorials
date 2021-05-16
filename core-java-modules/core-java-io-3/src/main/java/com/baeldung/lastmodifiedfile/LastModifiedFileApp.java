package com.baeldung.lastmodifiedfile;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.FileFilterUtils;

public class LastModifiedFileApp {

    public static File findUsingIOApi(String sdir) {
        File dir = new File(sdir);
        if (dir.isDirectory()) {
            Optional<File> opFile = Arrays.stream(dir.listFiles(File::isFile))
              .max((f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));

            if (opFile.isPresent()) {
                return opFile.get();
            }
        }

        return null;
    }

    public static Path findUsingNIOApi(String sdir) throws IOException {
        Path dir = Paths.get(sdir);
        if (Files.isDirectory(dir)) {
            Optional<Path> opPath = Files.list(dir)
              .filter(p -> !Files.isDirectory(p))
              .sorted((p1, p2) -> Long.valueOf(p2.toFile().lastModified())
                .compareTo(p1.toFile().lastModified()))
              .findFirst();

            if (opPath.isPresent()) {
                return opPath.get();
            }
        }

        return null;
    }

    public static File findUsingCommonsIO(String sdir) {
        File dir = new File(sdir);
        if (dir.isDirectory()) {
            File[] dirFiles = dir.listFiles((FileFilter) FileFilterUtils.fileFileFilter());
            if (dirFiles != null && dirFiles.length > 0) {
                Arrays.sort(dirFiles, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
                return dirFiles[0];
            }
        }

        return null;
    }

}
