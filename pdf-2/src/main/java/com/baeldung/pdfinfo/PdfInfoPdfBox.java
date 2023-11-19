package com.baeldung.pdfinfo;


import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDDocumentInformation;

import java.io.File;
import java.io.IOException;

public class PdfInfoPdfBox {

    public static int getNumberOfPages(final String pdfFile) throws IOException {
        File file = new File(pdfFile);
        PDDocument document = Loader.loadPDF(file);
        int pages = document.getNumberOfPages();
        document.close();
        return pages;
    }

    public static boolean isPasswordRequired(final String pdfFile) throws IOException {
        File file = new File(pdfFile);
        PDDocument document = Loader.loadPDF(file);
        boolean isEncrypted = document.isEncrypted();
        document.close();
        return isEncrypted;
    }

    public static PDDocumentInformation getInfo(final String pdfFile) throws IOException {
        File file = new File(pdfFile);
        PDDocument document = Loader.loadPDF(file);
        PDDocumentInformation info = document.getDocumentInformation();
        document.close();
        return info;
    }
}
