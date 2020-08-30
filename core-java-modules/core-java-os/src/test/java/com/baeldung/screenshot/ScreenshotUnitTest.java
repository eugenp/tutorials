import javax.imageio.ImageIO;
import java.awt.Component;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class ScreenshotUnitTest {

    @Test
    public void givenMainScreen_whenTakeScreenshot_thenSaveToFile() throws Exception {
        Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage capture = new Robot().createScreenCapture(screenRect);
        File imageFile = File.createTempFile("single-screen", "bmp");
        ImageIO.write(capture, "bmp", imageFile);
        assertTrue(imageFile.exists());
    }

    @Test
    public void givenMultipleScreens_whenTakeScreenshot_thenSaveToFile() throws Exception {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] screens = ge.getScreenDevices();
        Rectangle allScreenBounds = new Rectangle();
        for (GraphicsDevice screen : screens) {
            Rectangle screenBounds = screen.getDefaultConfiguration().getBounds();
            allScreenBounds.width += screenBounds.width;
            allScreenBounds.height = Math.max(allScreenBounds.height, screenBounds.height);
        }
        BufferedImage capture = new Robot().createScreenCapture(allScreenBounds);
        File imageFile = File.createTempFile("all-screens", "bmp");
        ImageIO.write(capture, "bmp", imageFile);
        assertTrue(imageFile.exists());
    }

    @Test
    public void givenComponent_whenTakeScreenshot_thenSaveToFile(Component component) throws Exception {
        Rectangle componentRect = component.getBounds();
        BufferedImage bufferedImage = new BufferedImage(componentRect.width, componentRect.height, BufferedImage.TYPE_INT_ARGB);
        component.paint(bufferedImage.getGraphics());
        File imageFile = File.createTempFile("component-screenshot", "bmp");
        ImageIO.write(bufferedImage, "bmp", imageFile);
        assertTrue(imageFile.exists());
    }

}