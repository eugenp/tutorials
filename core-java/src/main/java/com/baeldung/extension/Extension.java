package com.baeldung.extension;

import com.google.common.io.Files;
import org.apache.commons.io.FilenameUtils;

public class Extension {
    //Instead of file name we can also specify full path of a file eg. /baeldung/com/demo/abc.java
    public String getExtensionByApacheCommonLib(String filename) {
        return FilenameUtils.getExtension(filename);
    }

    public String getExtensionByStringHandling(String filename) {
        String fileExtension = "";
        if (filename.contains(".") && filename.lastIndexOf(".") != 0) {
            fileExtension = filename.substring(filename.lastIndexOf(".") + 1);
        }
        return fileExtension;
    }

    public String getExtensionByGuava(String filename) {
        return Files.getFileExtension(filename);
    }
}
