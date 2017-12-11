package com.baeldung.polymorphism;

public class ImageFile extends GenericFile {
    private int height;
    private int width;

    public ImageFile(String name, int height, int width, byte[] content, String version) {
        this.setHeight(height);
        this.setWidth(width);
        this.setContent(content);
        this.setName(name);
        this.setVersion(version);
        this.setExtension(".jpg");
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getFileInfo() {
        return String.format(" %s Height: %d\n Width: %d", super.getFileInfo(), this.getHeight(), this.getWidth());
    }

    public String read() {
        return this.getContent()
            .toString();
    }

}
