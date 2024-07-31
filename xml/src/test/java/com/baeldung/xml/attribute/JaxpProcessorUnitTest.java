package com.baeldung.xml.attribute;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.xmlunit.assertj.XmlAssert.assertThat;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.xpath.XPathExpressionException;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Unit test for {@link JaxpTransformer}.
 */
public class JaxpProcessorUnitTest {

    @Test
    public void givenXmlWithAttributes_whenModifyAttribute_thenGetXmlUpdated() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
        String path = getClass().getResource("/xml/attribute.xml")
            .toString();
        JaxpTransformer transformer = new JaxpTransformer(path);
        String attribute = "customer";
        String oldValue = "true";
        String newValue = "false";

        String result = transformer.modifyAttribute(attribute, oldValue, newValue);

        assertThat(result).hasXPath("//*[contains(@customer, 'false')]");
    }

    @Test
    public void givenXmlXee_whenInit_thenThrowException() throws IOException, SAXException, ParserConfigurationException, XPathExpressionException, TransformerFactoryConfigurationError, TransformerException {
        String path = getClass().getResource("/xml/xee_attribute.xml")
            .toString();

        assertThatThrownBy(() -> {

            new JaxpTransformer(path);

        }).isInstanceOf(SAXParseException.class);
    }

}
