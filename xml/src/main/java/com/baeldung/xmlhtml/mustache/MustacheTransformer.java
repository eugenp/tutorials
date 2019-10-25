package com.baeldung.xmlhtml.mustache;

import com.baeldung.xmlhtml.stax.StaxTransformer;
import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

public class MustacheTransformer {
    private StaxTransformer staxTransformer;
    private String templateFile;

    public MustacheTransformer(StaxTransformer staxTransformer, String templateFile) {
        this.staxTransformer = staxTransformer;
        this.templateFile = templateFile;
    }

    public String html() throws IOException {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(templateFile);
        try (Writer output = new StringWriter()) {
            mustache.execute(output, staxTransformer.getMap());
            output.flush();
            return output.toString();
        }
    }
}
