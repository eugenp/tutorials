package com.baeldung.hexagonal.arch;

class ImageStorage {
    void upload(Image image) {
        // put image into storage
    }

    Image download(String name) {
        return new Image();
    }

    Boolean has(String name) {
        return true;
    }
}
