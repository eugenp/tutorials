package com.baeldung.tesseract;

import java.io.File;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.Tesseract1;
import net.sourceforge.tess4j.TesseractException;

public class TesseractTess4JExample {
    
    public static void main(String[] args) {
        File imageFile = new File("src/main/java/com/baeldung/tesseract/baeldung.png");
        //File imageFile = new File("src/main/java/com/baeldung/tesseract/OAlquimista.png");
        Tesseract tesseract = new Tesseract();
        tesseract.setLanguage("spa");
        tesseract.setPageSegMode(1);
        tesseract.setDatapath("tessdata");
        String result = null;
        try {
            result = tesseract.doOCR(imageFile);
        } catch (TesseractException e) {
            e.printStackTrace();
        }
        System.out.println(result);
    }

}
