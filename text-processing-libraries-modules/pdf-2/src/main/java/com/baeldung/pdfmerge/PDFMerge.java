package com.baeldung.pdfmerge;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.io.RandomAccessStreamCacheImpl;
import org.apache.pdfbox.multipdf.PDFMergerUtility;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;

public class PDFMerge {

    public void mergeUsingPDFBox(List<String> pdfFiles, String outputFile) throws IOException {
        PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();
        pdfMergerUtility.setDestinationFileName(outputFile);

        pdfFiles.forEach(file -> {
            try {
                pdfMergerUtility.addSource(new File(file));
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        });

        pdfMergerUtility.mergeDocuments(RandomAccessStreamCacheImpl::new);
    }

    public void mergeUsingIText(List<String> pdfFiles, String outputFile) throws IOException, DocumentException {
        List<PdfReader> pdfReaders = List.of(new PdfReader(pdfFiles.get(0)), new PdfReader(pdfFiles.get(1)));
        Document document = new Document();
        FileOutputStream fos = new FileOutputStream(outputFile);
        PdfWriter writer = PdfWriter.getInstance(document, fos);
        document.open();
        PdfContentByte directContent = writer.getDirectContent();
        PdfImportedPage pdfImportedPage;
        for (PdfReader pdfReader : pdfReaders) {
            int currentPdfReaderPage = 1;
            while (currentPdfReaderPage <= pdfReader.getNumberOfPages()) {
                document.newPage();
                pdfImportedPage = writer.getImportedPage(pdfReader, currentPdfReaderPage);
                directContent.addTemplate(pdfImportedPage, 0, 0);
                currentPdfReaderPage++;
            }
        }
        fos.flush();
        document.close();
        fos.close();
    }
}
