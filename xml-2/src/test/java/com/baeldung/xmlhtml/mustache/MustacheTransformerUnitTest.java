package com.baeldung.xmlhtml.mustache;

import com.baeldung.xmlhtml.stax.StaxTransformer;
import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class MustacheTransformerUnitTest {

    @Test
    public void givenXml_whenTransform_thenGetHtml() throws IOException, URISyntaxException, XMLStreamException {
        String expectedHtml = new String(Files.readAllBytes((Paths.get(getClass()
          .getResource("/xmlhtml/notification.html")
          .toURI()))));
        StaxTransformer staxTransformer = new StaxTransformer("src/test/resources/xmlhtml/notification.xml");
        String templateFile = "src/test/resources/templates/template.mustache";
        MustacheTransformer transformer = new MustacheTransformer(staxTransformer, templateFile);

        String result = transformer.html();

        assertThat(result).isEqualTo(expectedHtml);
    }

}
