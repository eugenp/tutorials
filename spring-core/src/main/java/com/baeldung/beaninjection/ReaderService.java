package com.baeldung.beaninjection;

public class ReaderService {
    private FtpReader ftpReader;

    public void setFtpReader(FtpReader reader) {
        this.ftpReader = reader;
    }

    public FtpReader getFtpReader() {
        return ftpReader;
    }

}
