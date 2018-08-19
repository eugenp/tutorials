package com.baeldung.extension;

import com.google.common.io.Files;
import org.apache.commons.io.FilenameUtils;

import java.util.Optional;

public class Extension {
    //Instead of file name we can also specify full path of a file eg. /baeldung/com/demo/abc.java
    public String getExtensionByApacheCommonLib(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    public Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                       .filter(f -> f.contains("."))
                      .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    public String getExtensionByGuava(String filename) {
        return Files.getFileExtension(filename);
    }
}
