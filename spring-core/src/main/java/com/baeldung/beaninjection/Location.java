package com.baeldung.beaninjection;

import org.springframework.stereotype.Component;

@Component
public class Location {
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
