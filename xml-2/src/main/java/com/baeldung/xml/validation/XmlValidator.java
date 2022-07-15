package com.baeldung.xml.validation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class XmlValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(XmlValidator.class);

    private String xsdPath;
    private String xmlPath;

    public XmlValidator(String xsdPath, String xmlPath) {
        this.xsdPath = xsdPath;
        this.xmlPath = xmlPath;
    }

    public boolean isValid() throws IOException, SAXException {
        Validator validator = initValidator(xsdPath);
        try {
            validator.validate(new StreamSource(getFile(xmlPath)));
            return true;
        } catch (SAXException e) {
            return false;
        }
    }

    public List<SAXParseException> listParsingExceptions() throws IOException, SAXException {
        XmlErrorHandler xsdErrorHandler = new XmlErrorHandler();
        Validator validator = initValidator(xsdPath);
        validator.setErrorHandler(xsdErrorHandler);
        try {
            validator.validate(new StreamSource(getFile(xmlPath)));
        } catch (SAXParseException e) {}
        xsdErrorHandler.getExceptions().forEach(e -> LOGGER.info(e.getMessage()));
        return xsdErrorHandler.getExceptions();
    }

    private Validator initValidator(String xsdPath) throws SAXException {
        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
        Source schemaFile = new StreamSource(getFile(xsdPath));
        Schema schema = factory.newSchema(schemaFile);
        return schema.newValidator();
    }

    private File getFile(String location) {
        return new File(getClass().getClassLoader().getResource(location).getFile());
    }

}
