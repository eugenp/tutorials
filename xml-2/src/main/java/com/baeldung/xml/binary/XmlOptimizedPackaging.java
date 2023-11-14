package com.baeldung.xml.binary;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.namespace.QName;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;

@XmlRootElement
public class XmlOptimizedPackaging {

    @XmlElementRef(type = byte[].class)
    private JAXBElement<byte[]> binaryData;

    public XopExample(byte[] binaryData) {
        this.binaryData = new JAXBElement<>(new QName("binaryData"), byte[].class, binaryData);
    }

    public static void main(String[] args) throws JAXBException {
        // Read binary data from a file
        byte[] binaryData = // Read your binary data here

        // Create an instance of XopExample
        XopExample xopExample = new XopExample(binaryData);

        // Marshal to XML
        String xmlContent = marshalToXml(xopExample);

        // Your application logic here
    }

    private static String marshalToXml(Object obj) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, new StreamResult(writer));
        return writer.toString();
    }
}
