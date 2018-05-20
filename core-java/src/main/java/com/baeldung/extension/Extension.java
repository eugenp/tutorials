package com.baeldung.extension;

import com.google.common.io.Files;
import org.apache.commons.io.FilenameUtils;

public class Extension {
    //Instead of file name we can also specify full path of a file eg. /baeldung/com/demo/abc.java
    public String getExtensionByApacheCommonLib(String fileName) {
        return FilenameUtils.getExtension(fileName);
    }

    public String getExtensionByStringHandling(String fileName) {
        String fileExtension = "";
        if (fileName.contains(".") && fileName.lastIndexOf(".") != 0) {
            fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return fileExtension;
    }

    public String getExtensionByGuava(String fileName) {
        return Files.getFileExtension(fileName);
    }
}
