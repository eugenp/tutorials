package com.baeldung.pdfinfo;


import com.itextpdf.text.pdf.PdfReader;

import java.io.IOException;
import java.util.Map;

public class PdfInfoIText {

    public static int getNumberOfPages(final String pdfFile) throws IOException {
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

    public static Map<String, String> getInfo(final String pdfFile) throws IOException {
        PdfReader reader = new PdfReader(pdfFile);
        Map<String, String> info = reader.getInfo();
        reader.close();
        return info;
    }
}
