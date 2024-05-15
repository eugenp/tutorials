package com.baeldung.xmlhtml.jaxp;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

public class JaxpTransformerUnitTest {

    @Test
    public void givenXml_whenTransform_thenGetHtml() throws IOException, SAXException, ParserConfigurationException, TransformerException, URISyntaxException {
        String path = getClass()
          .getResource("/xmlhtml/notification.xml")
          .toString();
        String expectedHtml = new String(Files.readAllBytes((Paths.get(getClass()
          .getResource("/xmlhtml/notification.html")
          .toURI()))));
        JaxpTransformer transformer = new JaxpTransformer(path);

        String result = transformer
          .html()
          .replaceAll("(?m)^\\s+", "");//Delete extra spaces added by Java 11

        assertThat(result).isEqualTo(expectedHtml);
    }
}
