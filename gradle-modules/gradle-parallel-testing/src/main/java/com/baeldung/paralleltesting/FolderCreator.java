package com.baeldung.paralleltesting;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class FolderCreator {

    Boolean createFolder(Path path, String name) throws IOException {
        String newFolder = path.toAbsolutePath() + name;
        File f = new File(newFolder);
        return f.mkdir();
    }
}
