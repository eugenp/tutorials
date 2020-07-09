package com.baeldung.hexagonal.infrastructure;

import java.io.FileNotFoundException;
import java.util.Formatter;

import com.baeldung.hexagonal.domain.OutputService;
import com.baeldung.hexagonal.domain.TextInfo;

public class FileOutputService implements OutputService {

    private String fileName;

    public FileOutputService(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public void showInfo(TextInfo textInfo) {
        try (Formatter formatter = new Formatter(fileName)) {
            formatter.format(textInfo.toString());
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }

}
