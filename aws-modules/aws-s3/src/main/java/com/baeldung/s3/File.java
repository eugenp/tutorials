package com.baeldung.s3;

import java.nio.ByteBuffer;

public class File {
    private String name;
    private ByteBuffer content;

    public File(String name, ByteBuffer content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public ByteBuffer getContent() {
        return content;
    }
}
