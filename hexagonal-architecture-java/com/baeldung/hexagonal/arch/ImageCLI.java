package com.baeldung.hexagonal.arch;

class ImageCLI {
    private static ImageManager imageManager = new ImageManager(new ImageStorage());

    public static void main(String[] args) {
        if (args[0].equals("--upload")) {
            try {
                Image image = new Image(); // or read using FileUtil.read(args[1])
                imageManager.put(image);
            }
            catch (Exception e) {
                throw new RuntimeException("Error while uploading image: " + e.getMessage());
            }
        }
        else if (args[0].equals("--download")) {
            try {
                Image image = imageManager.get(args[1]);
                // FileUtils.write(image, "/tmp/");
            }
            catch (Exception e) {
                throw new RuntimeException("Error while downloading image: " + e.getMessage());
            }
        }
    }
}
