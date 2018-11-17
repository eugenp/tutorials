package com.baeldung.domain;

//@Data
//lombok is not working. will integrate it later
public class FileWriterResponse {

    private String fileMessage;
    private String date;

    public String getFileMessage() {
        return fileMessage;
    }

    public void setFileMessage(String fileMessage) {
        this.fileMessage = fileMessage;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
