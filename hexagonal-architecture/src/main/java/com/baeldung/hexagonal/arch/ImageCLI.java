class ImageCLI {
    private ImageManager imageManager = new ImagerManager(new ImageStorage());
    public static void main(String[] args) {
        if ( args[0] == "--upload") {
            try {
                manager.put(FileUtil.read(args[1]));
            } catch(Exception e) {
                throw new Exception("Error while uploading image: " + e.getMessage());
            }
        } else if ( args[0] == "--download") {
            try {
                Image image = manager.get(args[1]);
                FileUtils.write(image, "/tmp/");
            } catch(Exception e) {
                throw new Exception("Error while downloading image: " + e.getMessage());
            }
        }
    }
}
