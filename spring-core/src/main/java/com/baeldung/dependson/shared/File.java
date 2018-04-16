package com.baeldung.dependson.shared;

import org.springframework.stereotype.Service;
/**
 * Only for testing purpose
 * @author Sachin Pandey
 *
 */
@Service
public class File {

    private String text = "";

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = this.text+text;
    }
}
