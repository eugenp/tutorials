package com.baeldung.hexagonal;

public class TestExtractorAdapter implements ExtractorPort {
    private String data;

    TestExtractorAdapter(String data) {
        this.data = data;
    }

    public byte[] extractData() {
        return data.getBytes();
    }
}
