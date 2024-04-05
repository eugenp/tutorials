package com.baeldung.deepvsshallowcopy;

public class Letter {

    private String content;

    public Letter(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Letter{");
        sb.append("content='").append(content).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
