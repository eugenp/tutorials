package com.baeldung.xml.attribute;

import org.dom4j.DocumentException;
import org.junit.jupiter.api.Test;

import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.xmlunit.assertj.XmlAssert.assertThat;

/**
 * Unit test for {@link Dom4jTransformer}.
 */
public class Dom4jProcessorUnitTest {

    @Test
    public void givenXmlWithAttributes_whenModifyAttribute_thenGetXmlUpdated() throws TransformerFactoryConfigurationError, TransformerException, DocumentException {
        String path = getClass()
          .getResource("/xml/attribute.xml")
          .toString();
        Dom4jTransformer transformer = new Dom4jTransformer(path);
        String attribute = "customer";
        String oldValue = "true";
        String newValue = "false";

        String result = transformer.modifyAttribute(attribute, oldValue, newValue);

        assertThat(result).hasXPath("//*[contains(@customer, 'false')]");
    }

    @Test
    public void givenTwoXml_whenModifyAttribute_thenGetSimilarXml() throws IOException, TransformerFactoryConfigurationError, TransformerException, URISyntaxException, DocumentException {
        String path = getClass()
          .getResource("/xml/attribute.xml")
          .toString();
        Dom4jTransformer transformer = new Dom4jTransformer(path);
        String attribute = "customer";
        String oldValue = "true";
        String newValue = "false";
        String expectedXml = new String(Files.readAllBytes((Paths.get(getClass()
          .getResource("/xml/attribute_expected.xml")
          .toURI()))));

        String result = transformer.modifyAttribute(attribute, oldValue, newValue);

        assertThat(result)
          .and(expectedXml)
          .areSimilar();
    }

}
