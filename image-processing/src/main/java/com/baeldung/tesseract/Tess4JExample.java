package com.baeldung.tesseract;

import java.awt.Rectangle;
import java.io.File;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

public class Tess4JExample {

    public static void main(String[] args) {
        String result = null;
        try {
            File image = new File("src/main/resources/images/baeldung.png");
            Tesseract tesseract = new Tesseract();
            tesseract.setLanguage("spa");
            tesseract.setPageSegMode(1);
            tesseract.setOcrEngineMode(1);
            tesseract.setHocr(true);
            tesseract.setDatapath("src/main/resources/tessdata");
            result = tesseract.doOCR(image, new Rectangle(1200, 200));
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }

}
