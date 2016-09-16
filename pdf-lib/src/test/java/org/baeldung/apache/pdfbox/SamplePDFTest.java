package org.baeldung.apache.pdfbox;

public class SamplePDFTest{

    private static String extractPdfText(byte[] pdfData) throws IOException {
        PDDocument pdfDocument = PDDocument.load(new ByteArrayInputStream(pdfData));
        try {
           return new PDFTextStripper().getText(pdfDocument);
        } finally {
           pdfDocument.close();
        }
     }

    @Test
    public void testAssertContentContains() {
        assertThat(extractPdfText(pdfData)).contains("a text").contains("another text");
    }

}
