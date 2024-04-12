package com.baeldung.xsltProcessing;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class XSLTProcessorWithTemplate {
    public static void transformXMLUsingTemplate(String inputXMLPath, String xsltPath, String outputHTMLPath) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Source xsltSource = new StreamSource(new File(xsltPath));
        Templates templates = transformerFactory.newTemplates(xsltSource);

        Transformer transformer = templates.newTransformer();

        Source xmlSource = new StreamSource(new File(inputXMLPath));
        Result outputResult = new StreamResult(new File(outputHTMLPath));

        transformer.transform(xmlSource, outputResult);
    }
}
