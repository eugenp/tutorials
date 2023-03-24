package com.baeldung.pdfedition;

import java.io.IOException;
import java.net.MalformedURLException;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.forms.fields.PdfTextFormField;
import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfString;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.annot.PdfAnnotation;
import com.itextpdf.kernel.pdf.annot.PdfTextAnnotation;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.UnitValue;

public class PdfEditor {

    private static final String SOURCE = "src/main/resources/baeldung.pdf";
    private static final String DESTINATION = "src/main/resources/baeldung-modified.pdf";

    public static void main(String[] args) throws IOException {
        PdfReader reader = new PdfReader(SOURCE);
        PdfWriter writer = new PdfWriter(DESTINATION);
        PdfDocument pdfDocument = new PdfDocument(reader, writer);
        addContentToDocument(pdfDocument);
    }

    private static void addContentToDocument(PdfDocument pdfDocument) throws MalformedURLException {
        // 4.1. add form
        PdfFormField personal = PdfFormField.createEmptyField(pdfDocument);
        personal.setFieldName("information");
        PdfTextFormField name = PdfFormField.createText(pdfDocument, new Rectangle(35, 400, 100, 30), "name", "");
        personal.addKid(name);
        PdfAcroForm.getAcroForm(pdfDocument, true)
            .addField(personal, pdfDocument.getFirstPage());

        // 4.2. add new page
        pdfDocument.addNewPage(1);

        // 4.3. add annotation
        PdfAnnotation ann = new PdfTextAnnotation(new Rectangle(40, 435, 0, 0)).setTitle(new PdfString("name"))
            .setContents("Your name");
        pdfDocument.getPage(2)
            .addAnnotation(ann);

        // create document form pdf document
        Document document = new Document(pdfDocument);

        // 4.4. add an image
        ImageData imageData = ImageDataFactory.create("src/main/resources/baeldung.png");
        Image image = new Image(imageData).scaleAbsolute(550, 100)
            .setFixedPosition(1, 10, 50);
        document.add(image);

        // 4.5. add a paragraph
        Text title = new Text("This is a demo").setFontSize(16);
        Text author = new Text("Baeldung tutorials.");
        Paragraph p = new Paragraph().setFontSize(8)
            .add(title)
            .add(" from ")
            .add(author);
        document.add(p);

        // 4.6. add a table
        Table table = new Table(UnitValue.createPercentArray(2));
        table.addHeaderCell("#");
        table.addHeaderCell("company");
        table.addCell("name");
        table.addCell("baeldung");
        document.add(table);

        // close the document
        // this automatically closes the pdfDocument, which then closes automatically the pdfReader and pdfWriter
        document.close();
    }

}
