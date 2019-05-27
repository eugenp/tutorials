package com.baeldung.xml.attribute;

import static org.xmlunit.assertj.XmlAssert.assertThat;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPathExpressionException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class JaxpProcessorUnitTest {

    @Test
    public void givenXml_whenChangeAttributeWithJaxp_getXmlUpdated() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException, TransformerFactoryConfigurationError, TransformerException, URISyntaxException {
        String input = getContents("/xml/attribute.xml");
        String outputExpected = getContents("/xml/attribute_expected.xml");
        JaxpTransformer transformer = new JaxpTransformer();

        String output = transformer.setCustomerAttribute(input,"true","false");

        assertThat(output).and(outputExpected).areIdentical();
    }

    String getContents(String fileName) throws IOException, URISyntaxException {
        Path path = Paths.get(getClass()
          .getResource(fileName)
          .toURI());
        return new String(Files.readAllBytes(path), StandardCharsets.UTF_8);
    }

}
