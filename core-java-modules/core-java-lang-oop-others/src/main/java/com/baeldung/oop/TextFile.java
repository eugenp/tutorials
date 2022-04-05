package com.baeldung.oop;

public class TextFile extends GenericFile {
    private int wordCount;

    public TextFile(String name, String content, String version) {
        String[] words = content.split(" ");
        this.setWordCount(words.length > 0 ? words.length : 1);
        this.setContent(content.getBytes());
        this.setName(name);
        this.setVersion(version);
        this.setExtension(".txt");
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public String getFileInfo() {
        return "Text File Impl";
    }

    public String read() {
        return this.getContent()
            .toString();
    }

    public String read(int limit) {
        return this.getContent()
            .toString()
            .substring(0, limit);
    }

    public String read(int start, int stop) {
        return this.getContent()
            .toString()
            .substring(start, stop);
    }

}
