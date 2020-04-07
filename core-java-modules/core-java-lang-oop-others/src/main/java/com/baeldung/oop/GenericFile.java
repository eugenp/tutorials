package com.baeldung.oop;

import java.util.Date;

public class GenericFile {
    private String name;
    private String extension;
    private Date dateCreated;
    private String version;
    private byte[] content;

    public GenericFile() {
        this.setDateCreated(new Date());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

    public String getFileInfo() {
        return "Generic File Impl";
    }

    public Object read() {
        return content;
    }
}
