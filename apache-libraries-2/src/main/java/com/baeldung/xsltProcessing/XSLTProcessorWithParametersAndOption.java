package com.baeldung.xsltProcessing;

import javax.xml.transform.*;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;

public class XSLTProcessorWithParametersAndOption {
    public static void transformXMLWithParametersAndOption(
            String inputXMLPath,
            String xsltPath,
            String outputHTMLPath,
            String companyName,
            boolean enableIndentation
    ) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Source xsltSource = new StreamSource(new File(xsltPath));
        Transformer transformer = transformerFactory.newTransformer(xsltSource);

        transformer.setParameter("companyName", companyName);

        if (enableIndentation) {
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        }

        Source xmlSource = new StreamSource(new File(inputXMLPath));
        Result outputResult = new StreamResult(new File(outputHTMLPath));

        transformer.transform(xmlSource, outputResult);
    }
}
