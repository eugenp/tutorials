package com.baeldung.tesseract;

import org.bytedeco.javacpp.BytePointer;
import org.bytedeco.leptonica.PIX;
import org.bytedeco.tesseract.TessBaseAPI;
import org.bytedeco.tesseract.Tesseract;

public class TesseractPlatformExample {

    @SuppressWarnings("resource")
    public static void main(String[] args) {
        try {
            TessBaseAPI tessApi = new TessBaseAPI();
            tessApi.Init("src/main/resources/tessdata", "eng", 3);
            tessApi.SetPageSegMode(1);
            PIX image = org.bytedeco.leptonica.global.lept.pixRead("src/main/resources/images/baeldung.png");
            tessApi.SetImage(image);

            BytePointer outText = tessApi.GetUTF8Text();
            System.out.println(outText.getString());
            tessApi.End();
            
            
            Tesseract t = new Tesseract();
            t.
            
        } catch(Exception e) {
           e.printStackTrace();
        }
        
    }

}
