package com.baeldung.beaninjection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReaderManager {
    private FileReader fileReader;
    private FtpReader ftpReader;

    @Autowired
    public ReaderManager(FtpReader ftpReader) {
        this.ftpReader = ftpReader;
    }

    @Autowired
    public void setFileReader(FileReader fileReader) {
        this.fileReader = fileReader;
    }

    public FileReader getFileReader() {
        return fileReader;
    }

    public void setFtpReader(FtpReader ftpReader) {
        this.ftpReader = ftpReader;
    }

    public FtpReader getFtpReader() {
        return ftpReader;
    }

}
