package com.baeldung.beaninjectiontypes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.io.File;

public class FileUploaderSetterInjection {

    @Autowired
    @Qualifier("pdfFileChecker")
    private FileChecker fileChecker;

    //OR
    @Autowired
    @Qualifier("pdfFileChecker")
    public void setFileChecker(FileChecker fileChecker) {
        this.fileChecker = fileChecker;
    }

    public boolean handleFileUpload(File file) {
        return fileChecker.checkFile(file);
    }

}
