package enginetestkit;

public class Display {

    private final Platform platform;
    private final int height;

    public Display(Platform platform, int height) {
        this.platform = platform;
        this.height = height;
    }

    public Platform getPlatform() {
        return platform;
    }

    public int getHeight() {
        return height;
    }

}
