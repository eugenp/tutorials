package com.baeldung.xml.util;

import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class JaxbConverter<T> {

    private final Unmarshaller unmarshaller;
    private final Marshaller marshaller;

    public JaxbConverter(Class<T> type) {
        try {
            JAXBContext context = JAXBContext.newInstance(type);
            unmarshaller = context.createUnmarshaller();
            marshaller = context.createMarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public  <T> T convertFromXml(final String xml)  {
        StringReader reader = new StringReader(xml);
        try {
            return (T) unmarshaller.unmarshal(reader);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    public String convertToXml(T object) {
        final StringWriter writer = new StringWriter();
        try {
            marshaller.marshal(object, writer);
            return writer.toString();
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
