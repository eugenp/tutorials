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

public class LastModifiedFile {
    
    public static File findUsingIOApi(String sdir) {
        File lastmodfile = null;
        File dir = new File(sdir);
        if (dir.isDirectory()) {

            Optional<File> opFile = Arrays.stream(dir.listFiles(File::isFile))
                .max((f1, f2) -> Long.compare(f1.lastModified(), f2.lastModified()));

            if (opFile.isPresent())
                return opFile.get();

        }

        return lastmodfile;
    }
    
    public static Path findUsingNIOApi(String sdir) throws IOException {
        Path lastmodpath = null;
        Path dir = Paths.get(sdir);
        if (Files.isDirectory(dir)) {

            Optional<Path> opPath = Files.list(dir)
                .filter(p -> !Files.isDirectory(p))
                .sorted((p1, p2)-> Long.valueOf(p2.toFile().lastModified()).compareTo(p1.toFile().lastModified()))
                .findFirst();

            if (opPath.isPresent())
                return opPath.get();

        }

        return lastmodpath;
    }
    
    public static File findUsingCommonsIO(String sdir) {
        File lastmodfile = null;
        File dir = new File(sdir);
        if (dir.isDirectory()) {

            File[] dirfiles = dir.listFiles((FileFilter) FileFilterUtils.fileFileFilter());
            if (dirfiles != null && dirfiles.length > 0) {
                Arrays.sort(dirfiles, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
                lastmodfile = dirfiles[0];
            }
        }

        return lastmodfile;
    }

}
