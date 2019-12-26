package com.baeldung.xmlhtml.freemarker;

import com.baeldung.xmlhtml.stax.StaxTransformer;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class FreemarkerTransformer {
    private StaxTransformer staxTransformer;
    private String templateDirectory;
    private String templateFile;

    public FreemarkerTransformer(StaxTransformer staxTransformer, String templateDirectory, String templateFile) {
        this.staxTransformer = staxTransformer;
        this.templateDirectory = templateDirectory;
        this.templateFile = templateFile;
    }

    public String html() throws IOException, TemplateException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_29);
        cfg.setDirectoryForTemplateLoading(new File(templateDirectory));
        cfg.setDefaultEncoding(StandardCharsets.UTF_8.toString());
        cfg.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
        cfg.setLogTemplateExceptions(false);
        cfg.setWrapUncheckedExceptions(true);
        cfg.setFallbackOnNullLoopVariable(false);
        Template temp = cfg.getTemplate(templateFile);
        try (Writer output = new StringWriter()) {
            temp.process(staxTransformer.getMap(), output);
            return output.toString();
        }
    }
}
