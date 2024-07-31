package com.baeldung.poi.word;

import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordDocument {
    public static String logo = "logo-leaf.png";
    public static String paragraph1 = "poi-word-para1.txt";
    public static String paragraph2 = "poi-word-para2.txt";
    public static String paragraph3 = "poi-word-para3.txt";
    public static String output = "rest-with-spring.docx";

    public void handleSimpleDoc() throws Exception {
        XWPFDocument document = new XWPFDocument();

        XWPFParagraph title = document.createParagraph();
        title.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleRun = title.createRun();
        titleRun.setText("Build Your REST API with Spring");
        titleRun.setColor("009933");
        titleRun.setBold(true);
        titleRun.setFontFamily("Courier");
        titleRun.setFontSize(20);

        XWPFParagraph subTitle = document.createParagraph();
        subTitle.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun subTitleRun = subTitle.createRun();
        subTitleRun.setText("from HTTP fundamentals to API Mastery");
        subTitleRun.setColor("00CC44");
        subTitleRun.setFontFamily("Courier");
        subTitleRun.setFontSize(16);
        subTitleRun.setTextPosition(20);
        subTitleRun.setUnderline(UnderlinePatterns.DOT_DOT_DASH);

        XWPFParagraph image = document.createParagraph();
        image.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun imageRun = image.createRun();
        imageRun.setTextPosition(20);
        Path imagePath = Paths.get(ClassLoader.getSystemResource(logo).toURI());
        imageRun.addPicture(Files.newInputStream(imagePath), XWPFDocument.PICTURE_TYPE_PNG, imagePath.getFileName().toString(), Units.toEMU(50), Units.toEMU(50));

        XWPFParagraph sectionTitle = document.createParagraph();
        XWPFRun sectionTRun = sectionTitle.createRun();
        sectionTRun.setText("What makes a good API?");
        sectionTRun.setColor("00CC44");
        sectionTRun.setBold(true);
        sectionTRun.setFontFamily("Courier");

        XWPFParagraph para1 = document.createParagraph();
        para1.setAlignment(ParagraphAlignment.BOTH);
        String string1 = convertTextFileToString(paragraph1);
        XWPFRun para1Run = para1.createRun();
        para1Run.setText(string1);

        XWPFParagraph para2 = document.createParagraph();
        para2.setAlignment(ParagraphAlignment.RIGHT);
        String string2 = convertTextFileToString(paragraph2);
        XWPFRun para2Run = para2.createRun();
        para2Run.setText(string2);
        para2Run.setItalic(true);

        XWPFParagraph para3 = document.createParagraph();
        para3.setAlignment(ParagraphAlignment.LEFT);
        String string3 = convertTextFileToString(paragraph3);
        XWPFRun para3Run = para3.createRun();
        para3Run.setText(string3);

        FileOutputStream out = new FileOutputStream(output);
        document.write(out);
        out.close();
        document.close();
    }

    public String convertTextFileToString(String fileName) {
        try (Stream<String> stream = Files.lines(Paths.get(ClassLoader.getSystemResource(fileName).toURI()))) {
            return stream.collect(Collectors.joining(" "));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }
}
