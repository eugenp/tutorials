package com.baeldung.libraries.smooks.converter;

import com.baeldung.libraries.smooks.model.Order;
import org.milyn.Smooks;
import org.milyn.payload.JavaResult;
import org.milyn.payload.StringResult;
import org.xml.sax.SAXException;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;

public class OrderConverter {

    public Order convertOrderXMLToOrderObject(String path) throws IOException, SAXException {
        Smooks smooks = new Smooks(OrderConverter.class.getResourceAsStream("/smooks/smooks-mapping.xml"));
        try {
            JavaResult javaResult = new JavaResult();
            smooks.filterSource(new StreamSource(OrderConverter.class.getResourceAsStream(path)), javaResult);
            return (Order) javaResult.getBean("order");
        } finally {
            smooks.close();
        }
    }

    public String convertOrderXMLtoEDIFACT(String path) throws IOException, SAXException {
        return convertDocumentWithTempalte(path, "/smooks/smooks-transform-edi.xml");
    }

    public String convertOrderXMLtoEmailMessage(String path) throws IOException, SAXException {
        return convertDocumentWithTempalte(path, "/smooks/smooks-transform-email.xml");
    }

    private String convertDocumentWithTempalte(String path, String config) throws IOException, SAXException {
        Smooks smooks = new Smooks(config);

        try {
            StringResult stringResult = new StringResult();
            smooks.filterSource(new StreamSource(OrderConverter.class.getResourceAsStream(path)), stringResult);
            return stringResult.toString();
        } finally {
            smooks.close();
        }
    }
}
