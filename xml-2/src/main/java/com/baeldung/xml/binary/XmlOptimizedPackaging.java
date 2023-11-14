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
import java.util.Random;

@XmlRootElement
public class XmlOptimizedPackaging {

    @XmlElementRef(type = byte[].class)
    private JAXBElement<byte[]> binaryData;

    public XmlOptimizedPackaging() {
        int dataSize = 10; // specify the size you want
        byte[] sampleData = generateSampleBinaryData(dataSize);

        this.binaryData = new JAXBElement<>(new QName("binaryData"), byte[].class, sampleData);
    }

    public static void main(String[] args) throws JAXBException {
        // Create an instance of XmlOptimizedPackaging
        XmlOptimizedPackaging XmlOptimizedPackaging = new XmlOptimizedPackaging(binaryData);

        // Marshal to XML
        String xmlContent = marshalToXml(XmlOptimizedPackaging);

        // Your application logic here
    }

    private static String marshalToXml(Object obj) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(obj.getClass());
        Marshaller marshaller = context.createMarshaller();
        StringWriter writer = new StringWriter();
        marshaller.marshal(obj, new StreamResult(writer));
        return writer.toString();
    }

        private static byte[] generateSampleBinaryData(int size) {
        byte[] binaryData = new byte[size];
        new Random().nextBytes(binaryData);
        return binaryData;
    }
}
