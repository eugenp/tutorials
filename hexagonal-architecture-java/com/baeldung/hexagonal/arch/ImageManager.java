package com.baeldung.hexagonal.arch;

class ImageManager {
    private ImageStorage storage;

    ImageManager(ImageStorage storage) {
        this.storage = storage;
    }

    void put(Image image) {
        if (image.getSize() > 100) throw new RuntimeException("Image too large!");
        storage.upload(image);
    }

    Image get(String name) {
        if (!storage.has(name)) throw new RuntimeException("Image not found");
        return storage.download(name);
    }
}
