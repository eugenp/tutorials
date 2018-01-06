package com.baeldung.beaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.File;

public class FileUploaderConstructorInjection {

    private FileChecker fileChecker;

    @Autowired
    public FileUploaderConstructorInjection(@Qualifier("pdfFileChecker") FileChecker fileChecker) {
        this.fileChecker = fileChecker;
    }

    public boolean handleFileUpload(File file) {
        return fileChecker.checkFile(file);
    }

}
