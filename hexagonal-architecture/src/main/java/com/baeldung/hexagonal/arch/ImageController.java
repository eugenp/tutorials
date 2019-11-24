public ImageController {
    private ImageManager imageManager;
    
    public ImageController() {
        this.imageManager = new ImagerManager(new ImageStorage());
    }
    
    public void upload(HTTPRequest request) {
        try {
            imageManager.put(request.payload);
        } catch (Exception e) {
            return ResponseCode_500;
        }
    }
    public HTTPResponse download(HTTPRequest request) {
        try {
            Image image = imageManager.get(request.get("name));
            HTTPResponse response = new HTTPResponse();
            response.payload = image;
            return response;
        } catch(Exception e) {
            return ResponseCode_500;
        }
    }
}
