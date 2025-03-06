package com.baeldung.webflux.filerecord;

import org.springframework.data.annotation.Id;

import java.util.List;

public class FileRecord {
    @Id
    private int id;

    private List<String> filenames;

    public FileRecord(List<String> filenames) {
        this.filenames = filenames;
    }

    public FileRecord() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getFilenames() {
        return filenames;
    }

    public void setFilenames(List<String> filenames) {
        this.filenames = filenames;
    }
}
