package com.baeldung.xml.attribute;

import org.joox.JOOX;
import org.joox.Match;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.TransformerFactoryConfigurationError;
import java.io.IOException;

import static org.joox.JOOX.$;

public class JooxTransformer {

    private final Document input;

    public JooxTransformer(String resourcePath) throws SAXException, IOException {
        // 1- Build the doc from the XML file
        DocumentBuilder builder = JOOX.builder();
        input = builder.parse(resourcePath);
    }

    public String modifyAttribute(String attribute, String oldValue, String newValue) throws TransformerFactoryConfigurationError {
        // 2- Select the document
        Match $ = $(input);
        // 3 - Find node to modify
        String expr = String.format("//*[contains(@%s, '%s')]", attribute, oldValue);
        $
            // .find("to") or with xpath
            .xpath(expr)
            .get()
            .stream()
            .forEach(e -> e.setAttribute(attribute, newValue));
        // 4- Return result as String
        return $.toString();
    }
}
