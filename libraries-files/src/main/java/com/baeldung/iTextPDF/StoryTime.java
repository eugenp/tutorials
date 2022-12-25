package com.baeldung.iTextPDF;

import static com.itextpdf.layout.properties.TextAlignment.CENTER;
import static com.itextpdf.layout.properties.VerticalAlignment.TOP;
import static java.lang.Math.PI;

import java.io.File;
import java.io.IOException;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;

public class StoryTime {

    public static final String OUTPUT_FILE = "output/aliceupdated.pdf";

    public String alice = " Alice was a curious young girl who one day stumbled upon a strange, talking rabbit." + " The rabbit, in a hurry, invited Alice to follow him down a rabbit hole."
        + "Alice, not one to turn down an adventure, eagerly followed the rabbit and found herself tumbling down a long, dark tunnel.As she fell, Alice passed through a small door and into a fantastical world filled with talking animals and peculiar characters."
        + "She met the Cheshire Cat, who seemed to appear and disappear at will, and the Mad Hatter, who invited her to a tea party."
        + "Alice also encountered the tyrannical Queen of Hearts, who was quick to anger and ordered the beheading of anyone who crossed her."
        + "Despite the dangers, Alice remained determined to find her way home and eventually stumbled upon the key to the door that would take her back to the real world."
        + " As Alice stepped through the door and back into reality, she couldn't help but wonder if it had all been a dream. But the memories of her adventures in Wonderland stayed with her forever, and she often found herself longing to return to that strange and magical place.";

    public String paul = " Paulinho is a Brazilian professional footballer who currently plays as a midfielder for Guangzhou Evergrande Taobao in the Chinese Super League."
        + "He has also played for several top clubs around the world, including Barcelona, Tottenham Hotspur, and Guangzhou Evergrande. Paulinho has represented Brazil at the international level and has won several accolades throughout his career, including the Copa America and the Chinese Super League title."
        + "He is known for his strong work ethic and powerful offensive play.";

    public static void main(String[] args) throws IOException {
        File file = new File(OUTPUT_FILE);
        file.getParentFile()
            .mkdirs();

        // new StoryTime().createPdf();
        new StoryTime().addWatermarkToExistingPdf();

    }

    public void createPdf() throws IOException {
        StoryTime storyTime = new StoryTime();
        String waterMark = "CONFIDENTIAL";
        PdfWriter writer = new PdfWriter(storyTime.OUTPUT_FILE);
        PdfDocument pdf = new PdfDocument(writer);

        try (Document document = new Document(pdf)) {

            document.add(new Paragraph(storyTime.alice).setFont(PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN)));
            document.add(new Paragraph(storyTime.paul));

            Paragraph paragraph = storyTime.createWatermarkParagraph(waterMark);
            for (int i = 1; i <= document.getPdfDocument()
                .getNumberOfPages(); i++) {

                storyTime.addWatermarkToPage(document, i, paragraph, 0f);
            }
        }
    }

    public void addWatermarkToExistingPdf() throws IOException {
        StoryTime storyTime = new StoryTime();
        String outputPdf = "output/aliceupdated.pdf";
        String watermark = "CONFIDENTIAL";

        try (PdfDocument pdfDocument = new PdfDocument(new PdfReader("output/alicepaulwithoutwatermark.pdf"), new PdfWriter(outputPdf))) {
            Document document = new Document(pdfDocument);

            Paragraph paragraph = storyTime.createWatermarkParagraph(watermark);
            PdfExtGState transparentGraphicState = new PdfExtGState().setFillOpacity(0.5f);
            for (int i = 1; i <= document.getPdfDocument()
                .getNumberOfPages(); i++) {
                storyTime.addWatermarkToExistingPage(document, i, paragraph, transparentGraphicState, 0f);
            }
        }
    }

    public Paragraph createWatermarkParagraph(String watermark) throws IOException {
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        Text text = new Text(watermark);
        text.setFont(font);
        text.setFontSize(56);
        text.setOpacity(0.5f);

        return new Paragraph(text);
    }

    public void addWatermarkToPage(Document document, int pageIndex, Paragraph paragraph, float verticalOffset) {
        PdfPage pdfPage = document.getPdfDocument()
            .getPage(pageIndex);
        PageSize pageSize = (PageSize) pdfPage.getPageSizeWithRotation();

        float x = (pageSize.getLeft() + pageSize.getRight()) / 2;
        float y = (pageSize.getTop() + pageSize.getBottom()) / 2;
        float xOffset = 100f / 2;
        float rotationInRadians = (float) (PI / 180 * 45f);
        document.showTextAligned(paragraph, x - xOffset, y + verticalOffset, pageIndex, CENTER, TOP, rotationInRadians);
    }

    public void addWatermarkToExistingPage(Document document, int pageIndex, Paragraph paragraph, PdfExtGState graphicState, float verticalOffset) {
        PdfDocument pdfDoc = document.getPdfDocument();
        PdfPage pdfPage = pdfDoc.getPage(pageIndex);
        PageSize pageSize = (PageSize) pdfPage.getPageSizeWithRotation();

        float x = (pageSize.getLeft() + pageSize.getRight()) / 2;
        float y = (pageSize.getTop() + pageSize.getBottom()) / 2;
        PdfCanvas over = new PdfCanvas(pdfDoc.getPage(pageIndex));
        over.saveState();
        over.setExtGState(graphicState);
        float xOffset = 100f / 2;
        float rotationInRadians = (float) (PI / 180 * 45f);
        document.showTextAligned(paragraph, x - xOffset, y + verticalOffset, pageIndex, CENTER, TOP, rotationInRadians);
        document.flush();
        over.restoreState();
        over.release();
    }

}
