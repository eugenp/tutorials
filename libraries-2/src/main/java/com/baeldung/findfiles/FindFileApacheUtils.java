package com.baeldung.findfiles;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.apache.commons.io.filefilter.WildcardFileFilter;

public class FindFileApacheUtils {

    private FindFileApacheUtils() {
    }

    public static Iterator<File> find(Path startPath, String extension) {
        if (!Files.isDirectory(startPath)) {
            throw new IllegalArgumentException("Provided path is not a directory: " + startPath);
        }

        if (!extension.startsWith("."))
            extension = "." + extension;

        return FileUtils.iterateFiles(startPath.toFile(), WildcardFileFilter.builder()
            .setWildcards("*" + extension)
            .get(), TrueFileFilter.INSTANCE);
    }
}
