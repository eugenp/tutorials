package com.baeldung.poi.powerpoint;

import org.apache.poi.sl.usermodel.AutoNumberingScheme;
import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.sl.usermodel.TableCell;
import org.apache.poi.sl.usermodel.TextParagraph;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.SlideLayout;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFAutoShape;
import org.apache.poi.xslf.usermodel.XSLFHyperlink;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.apache.poi.xslf.usermodel.XSLFSlideLayout;
import org.apache.poi.xslf.usermodel.XSLFSlideMaster;
import org.apache.poi.xslf.usermodel.XSLFTable;
import org.apache.poi.xslf.usermodel.XSLFTableCell;
import org.apache.poi.xslf.usermodel.XSLFTableRow;
import org.apache.poi.xslf.usermodel.XSLFTextParagraph;
import org.apache.poi.xslf.usermodel.XSLFTextRun;
import org.apache.poi.xslf.usermodel.XSLFTextShape;

import java.awt.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for the PowerPoint presentation creation
 */
public class PowerPointHelper {

    /**
     * Read an existing presentation
     *
     * @param fileLocation
     *            File location of the presentation
     * @return instance of {@link XMLSlideShow}
     * @throws IOException
     */
    public XMLSlideShow readingExistingSlideShow(String fileLocation) throws IOException {
        return new XMLSlideShow(new FileInputStream(fileLocation));
    }

    /**
     * Create a sample presentation
     *
     * @param fileLocation
     *            File location of the presentation
     * @throws IOException
     */
    public void createPresentation(String fileLocation) throws IOException {
        // Create presentation
        XMLSlideShow ppt = new XMLSlideShow();

        XSLFSlideMaster defaultMaster = ppt.getSlideMasters().get(0);

        // Retriving the slide layout
        XSLFSlideLayout layout = defaultMaster.getLayout(SlideLayout.TITLE_ONLY);

        // Creating the 1st slide
        XSLFSlide slide1 = ppt.createSlide(layout);
        XSLFTextShape title = slide1.getPlaceholder(0);
        // Clearing text to remove the predefined one in the template
        title.clearText();
        XSLFTextParagraph p = title.addNewTextParagraph();

        XSLFTextRun r1 = p.addNewTextRun();
        r1.setText("Baeldung");
        r1.setFontColor(new Color(78, 147, 89));
        r1.setFontSize(48.);

        // Add Image
        ClassLoader classLoader = getClass().getClassLoader();
        byte[] pictureData = IOUtils.toByteArray(new FileInputStream(classLoader.getResource("logo-leaf.png").getFile()));

        XSLFPictureData pd = ppt.addPicture(pictureData, PictureData.PictureType.PNG);
        XSLFPictureShape picture = slide1.createPicture(pd);
        picture.setAnchor(new Rectangle(320, 230, 100, 92));

        // Creating 2nd slide
        layout = defaultMaster.getLayout(SlideLayout.TITLE_AND_CONTENT);
        XSLFSlide slide2 = ppt.createSlide(layout);

        // setting the tile
        title = slide2.getPlaceholder(0);
        title.clearText();
        XSLFTextRun r = title.addNewTextParagraph().addNewTextRun();
        r.setText("Baeldung");

        // Adding the link
        XSLFHyperlink link = r.createHyperlink();
        link.setAddress("http://www.baeldung.com");

        // setting the content
        XSLFTextShape content = slide2.getPlaceholder(1);
        content.clearText(); // unset any existing text
        content.addNewTextParagraph().addNewTextRun().setText("First paragraph");
        content.addNewTextParagraph().addNewTextRun().setText("Second paragraph");
        content.addNewTextParagraph().addNewTextRun().setText("Third paragraph");

        // Creating 3rd slide - List
        layout = defaultMaster.getLayout(SlideLayout.TITLE_AND_CONTENT);
        XSLFSlide slide3 = ppt.createSlide(layout);
        title = slide3.getPlaceholder(0);
        title.clearText();
        r = title.addNewTextParagraph().addNewTextRun();
        r.setText("Lists");

        content = slide3.getPlaceholder(1);
        content.clearText();
        XSLFTextParagraph p1 = content.addNewTextParagraph();
        p1.setIndentLevel(0);
        p1.setBullet(true);
        r1 = p1.addNewTextRun();
        r1.setText("Bullet");

        // the next three paragraphs form an auto-numbered list
        XSLFTextParagraph p2 = content.addNewTextParagraph();
        p2.setBulletAutoNumber(AutoNumberingScheme.alphaLcParenRight, 1);
        p2.setIndentLevel(1);
        XSLFTextRun r2 = p2.addNewTextRun();
        r2.setText("Numbered List Item - 1");

        // Creating 4th slide
        XSLFSlide slide4 = ppt.createSlide();
        createTable(slide4);

        // Save presentation
        FileOutputStream out = new FileOutputStream(fileLocation);
        ppt.write(out);
        out.close();

        // Closing presentation
        ppt.close();
    }

    /**
     * Delete a slide from the presentation
     *
     * @param ppt
     *            The presentation
     * @param slideNumber
     *            The number of the slide to be deleted (0-based)
     */
    public void deleteSlide(XMLSlideShow ppt, int slideNumber) {
        ppt.removeSlide(slideNumber);
    }

    /**
     * Re-order the slides inside a presentation
     *
     * @param ppt
     *            The presentation
     * @param slideNumber
     *            The number of the slide to move
     * @param newSlideNumber
     *            The new position of the slide (0-base)
     */
    public void reorderSlide(XMLSlideShow ppt, int slideNumber, int newSlideNumber) {
        List<XSLFSlide> slides = ppt.getSlides();

        XSLFSlide secondSlide = slides.get(slideNumber);
        ppt.setSlideOrder(secondSlide, newSlideNumber);
    }

    /**
     * Retrieve the placeholder inside a slide
     *
     * @param slide
     *          The slide
     * @return List of placeholder inside a slide
     */
    public List<XSLFShape> retrieveTemplatePlaceholders(XSLFSlide slide) {
        List<XSLFShape> placeholders = new ArrayList<>();

        for (XSLFShape shape : slide.getShapes()) {
            if (shape instanceof XSLFAutoShape) {
                placeholders.add(shape);
            }
        }
        return placeholders;
    }

    /**
     * Create a table
     *
     * @param slide
     *            Slide
     */
    private void createTable(XSLFSlide slide) {

        XSLFTable tbl = slide.createTable();
        tbl.setAnchor(new Rectangle(50, 50, 450, 300));

        int numColumns = 3;
        int numRows = 5;

        // header
        XSLFTableRow headerRow = tbl.addRow();
        headerRow.setHeight(50);
        for (int i = 0; i < numColumns; i++) {
            XSLFTableCell th = headerRow.addCell();
            XSLFTextParagraph p = th.addNewTextParagraph();
            p.setTextAlign(TextParagraph.TextAlign.CENTER);
            XSLFTextRun r = p.addNewTextRun();
            r.setText("Header " + (i + 1));
            r.setBold(true);
            r.setFontColor(Color.white);
            th.setFillColor(new Color(79, 129, 189));
            th.setBorderWidth(TableCell.BorderEdge.bottom, 2.0);
            th.setBorderColor(TableCell.BorderEdge.bottom, Color.white);
            // all columns are equally sized
            tbl.setColumnWidth(i, 150);
        }

        // data
        for (int rownum = 0; rownum < numRows; rownum++) {
            XSLFTableRow tr = tbl.addRow();
            tr.setHeight(50);
            for (int i = 0; i < numColumns; i++) {
                XSLFTableCell cell = tr.addCell();
                XSLFTextParagraph p = cell.addNewTextParagraph();
                XSLFTextRun r = p.addNewTextRun();

                r.setText("Cell " + (i * rownum + 1));
                if (rownum % 2 == 0) {
                    cell.setFillColor(new Color(208, 216, 232));
                } else {
                    cell.setFillColor(new Color(233, 247, 244));
                }
            }
        }
    }
}
