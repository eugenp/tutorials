package com.baeldung.pdf;

import com.lowagie.text.DocumentException;
import org.junit.Test;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

public class PDFThymeleafUnitTest {

    @Test
    public void givenThymeleafTemplate_whenParsedAndRenderedToPDF_thenItShouldNotBeEmpty() throws DocumentException, IOException {
        String html = parseThymeleafTemplate();
        ByteArrayOutputStream outputStream = generatePdfOutputStreamFromHtml(html);
        assertTrue(outputStream.size() > 0);
    }

    private ByteArrayOutputStream generatePdfOutputStreamFromHtml(String html) throws IOException, DocumentException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(outputStream);

        outputStream.close();
        return outputStream;
    }

    private String parseThymeleafTemplate() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();
        context.setVariable("to", "Baeldung.com");

        return templateEngine.process("thymeleaf_template", context);
    }
}
