/**
 * 
 */
package com.baeldung.wordtemplate;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.junit.BeforeClass;
import org.junit.Test;

import com.deepoove.poi.XWPFTemplate;
import com.deepoove.poi.config.Configure;
import com.deepoove.poi.config.ConfigureBuilder;
import com.deepoove.poi.plugin.comment.CommentRenderPolicy;

public class WordDocumentFromTemplateTest {

    private static WordDocumentFromTemplate docEditor;
    private InputStream inStream;
    private static ConfigureBuilder builder;

    private static Path outputPath;

    @BeforeClass
    public static void setUp() throws IOException {
        docEditor = new WordDocumentFromTemplate();

        Path resourcesPath = Paths.get("src", "main", "resources");

        outputPath = resourcesPath.resolve("output.docx")
            .toAbsolutePath();

        Files.deleteIfExists(outputPath);

        builder = Configure.builder();
        builder.bind("sample", new SampleRenderPolicy());
        builder.bind("comment", new CommentRenderPolicy());

        docEditor.titleTemplate();
        docEditor.textTagTemplate();
        docEditor.useDataModel();
        docEditor.imageTagTemplate();
        docEditor.numberingTagTemplate();
        docEditor.tableTagTemplate();
        docEditor.sectionTemplate();
        docEditor.nestingTemplate();
        docEditor.refrenceTags();
        docEditor.usingPlugins();
        docEditor.commentsplugin();
    }

    @Test
    public void givenTemplateFile_whenTemplateDataRendered_ThenTemplateTagsReplacedWithDataInOutput() throws IOException {
        inStream = WordDocumentFromTemplate.class.getClassLoader()
            .getResourceAsStream("template.docx");

        XWPFTemplate template = XWPFTemplate.compile(inStream, builder.build())
            .render(docEditor.getTemplateData());
        template.writeAndClose(new FileOutputStream(new File(outputPath.toString())));

        assertTrue(searchWordInOutPut("John"));
        assertTrue(searchWordInOutPut("Florida,USA"));
        assertTrue(searchWordInOutPut("This is sample plugin custom-plugin"));
    }

    public boolean searchWordInOutPut(String word) {
        boolean wordFound = false;
        try (FileInputStream fis = new FileInputStream(outputPath.toString()); XWPFDocument document = new XWPFDocument(fis)) {
            // Read paragraphs
            List<XWPFParagraph> paragraphs = document.getParagraphs();

            for (XWPFParagraph paragraph : paragraphs) {
                String text = paragraph.getText();
                if (text.contains(word)) {
                    wordFound = true;
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return wordFound;
    }
}
