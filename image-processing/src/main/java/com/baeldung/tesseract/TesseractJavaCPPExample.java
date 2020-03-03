package com.baeldung.tesseract;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.javacpp.lept.PIX;
import org.bytedeco.javacpp.tesseract.TessBaseAPI;
import static org.bytedeco.javacpp.lept.*;

public class TesseractJavaCPPExample {
    
    public static void main(String[] args) {
        BytePointer outText;

        TessBaseAPI api = new TessBaseAPI();
        if (api.Init("tessdata", "eng") != 0) {
            System.err.println("Could not initialize tesseract.");
            System.exit(1);
        }

        // Open input image with leptonica library
        //PIX image = pixRead("src/main/java/com/baeldung/tesseract/OAlquimista.png");
        PIX image = pixRead("src/main/java/com/baeldung/tesseract/baeldung.png");
        api.SetImage(image);
        
        
        
        // Get OCR result
        outText = api.GetUTF8Text();
        System.out.println("OCR output:\n" + outText.getString());

        // Destroy used object and release memory
        api.End();
        outText.deallocate();
        pixDestroy(image);
    }

}
