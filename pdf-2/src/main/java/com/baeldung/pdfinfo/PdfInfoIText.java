package com.baeldung.pdfinfo;


import com.itextpdf.text.pdf.PdfReader;

import java.io.IOException;
import java.util.HashMap;

public class PdfInfoIText {

    public static int getNumberOfPage(final String pdfFile) throws IOException {
        PdfReader reader = new PdfReader(pdfFile);
        int pages = reader.getNumberOfPages();
        reader.close();
        return pages;
    }

    public static boolean isPasswordRequired(final String pdfFile) throws IOException {
        PdfReader reader = new PdfReader(pdfFile);
        boolean isEncrypted = reader.isEncrypted();
        reader.close();
        return isEncrypted;
    }

    public static HashMap<String, String> getInfo(final String pdfFile) throws IOException {
        PdfReader reader = new PdfReader(pdfFile);
        HashMap<String, String> info = reader.getInfo();
        reader.close();
        return info;
    }
}
