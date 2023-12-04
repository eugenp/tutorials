package com.baeldung.findfiles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FindFileJava2Utils {

    private FindFileJava2Utils() {
    }

    public static List<File> find(File startPath, String extension) {
        if (!startPath.isDirectory()) {
            throw new IllegalArgumentException("Provided path is not a directory: " + startPath);
        }

        List<File> matches = new ArrayList<>();

        File[] files = startPath.listFiles();
        if (files == null)
            return matches;

        MatchExtensionPredicate filter = new MatchExtensionPredicate(extension);
        for (File file : files) {
            if (file.isDirectory()) {
                matches.addAll(find(file, extension));
            } else if (filter.test(file.toPath())) {
                matches.add(file);
            }
        }

        return matches;
    }
}
