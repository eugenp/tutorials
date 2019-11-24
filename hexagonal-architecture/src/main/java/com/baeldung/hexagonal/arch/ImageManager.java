class ImageManager {
    private ImageStorage storage;
    
    public ImageManager(ImageStorage storage) {
        this.storage = storage;
    }

    void put(Image image) {
        if (image.size > 100) throw new Exception("Image too large!");
        storage.upload(image);
    }

    Image get(String name) {
        if (!storage.has(name)) throw new Exception("Image not found");
        return storage.download(name);
    }
}
