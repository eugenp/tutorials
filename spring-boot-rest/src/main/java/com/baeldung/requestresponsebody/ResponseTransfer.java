package com.baeldung.requestresponsebody;

public class ResponseTransfer {

    private String text;

    public ResponseTransfer(String text) {
        this.setText(text);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}