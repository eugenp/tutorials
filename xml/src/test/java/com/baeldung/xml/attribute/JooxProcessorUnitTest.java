package com.baeldung.xml.attribute;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPathExpressionException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.xmlunit.assertj.XmlAssert.assertThat;

/**
 * Unit test for {@link JooxTransformer}.
 */
public class JooxProcessorUnitTest {

    @Test
    public void givenXmlWithAttributes_whenModifyAttribute_thenGetXmlUpdated() throws IOException, SAXException, TransformerFactoryConfigurationError {
        String path = getClass().getResource("/xml/attribute.xml")
            .toString();
        JooxTransformer transformer = new JooxTransformer(path);
        String attribute = "customer";
        String oldValue = "true";
        String newValue = "false";

        String result = transformer.modifyAttribute(attribute, oldValue, newValue);

        assertThat(result).hasXPath("//*[contains(@customer, 'false')]");
    }

    @Test
    public void givenTwoXml_whenModifyAttribute_thenGetSimilarXml() throws IOException, TransformerFactoryConfigurationError, URISyntaxException, SAXException {
        String path = getClass().getResource("/xml/attribute.xml")
            .toString();
        JooxTransformer transformer = new JooxTransformer(path);
        String attribute = "customer";
        String oldValue = "true";
        String newValue = "false";
        String expectedXml = new String(Files.readAllBytes((Paths.get(getClass().getResource("/xml/attribute_expected.xml")
            .toURI()))));

        String result = transformer
          .modifyAttribute(attribute, oldValue, newValue)
          .replaceAll("(?m)^[ \t]*\r?\n", "");//Delete extra spaces added by Java 11

        assertThat(result).and(expectedXml)
            .areSimilar();
    }

    @Test
    public void givenXmlXee_whenInit_thenThrowException() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
        String path = getClass().getResource("/xml/xee_attribute.xml")
            .toString();

        assertThatThrownBy(() -> {

            new JooxTransformer(path);

        }).isInstanceOf(SAXParseException.class);
    }

}
