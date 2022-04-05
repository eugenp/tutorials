package com.baeldung.xmlhtml.freemarker;

import com.baeldung.xmlhtml.stax.StaxTransformer;
import freemarker.template.TemplateException;
import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class FreemarkerTransformerUnitTest {

    @Test
    public void givenXml_whenTransform_thenGetHtml() throws IOException, URISyntaxException, XMLStreamException, TemplateException {
        String expectedHtml = new String(Files.readAllBytes((Paths.get(getClass()
          .getResource("/xmlhtml/notification.html")
          .toURI()))));
        StaxTransformer staxTransformer = new StaxTransformer("src/test/resources/xmlhtml/notification.xml");
        String templateFile = "freemarker.html";
        String templateDirectory = "src/test/resources/templates";
        FreemarkerTransformer transformer = new FreemarkerTransformer(staxTransformer, templateDirectory, templateFile);

        String result = transformer.html();

        assertThat(result).isEqualTo(expectedHtml);
    }

}
