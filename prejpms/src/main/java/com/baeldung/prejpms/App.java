package com.baeldung.prejpms;

import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.crypto.provider.SunJCE;

import sun.misc.BASE64Encoder;
import sun.reflect.Reflection;

public class App {

    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {

        getCrytpographyProviderName();
        getCallStackClassNames();
        getXmlFromObject(new Book(100, "Java Modules Architecture"));
        getBase64EncodedString("Java");
    }

    private static void getCrytpographyProviderName() {
        try {
            LOGGER.info("1. JCE Provider Name: {}\n", new SunJCE().getName());
        } catch (Throwable e) {
            LOGGER.error(e.toString());
        }
    }

    private static void getCallStackClassNames() {
        try {
            StringBuffer sbStack = new StringBuffer();
            int i = 0;
            Class<?> caller = Reflection.getCallerClass(i++);
            do {
                sbStack.append(i + ".")
                    .append(caller.getName())
                    .append("\n");
                caller = Reflection.getCallerClass(i++);
            } while (caller != null);
            LOGGER.info("2. Call Stack:\n{}", sbStack);
        } catch (Throwable e) {
            LOGGER.error(e.toString());
        }
    }

    private static void getXmlFromObject(Book book) {
        try {
            Marshaller marshallerObj = JAXBContext.newInstance(Book.class)
                .createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter sw = new StringWriter();
            marshallerObj.marshal(book, sw);
            LOGGER.info("3. Xml for Book object:\n{}", sw);
        } catch (Throwable e) {
            LOGGER.error(e.toString());
        }

    }

    private static void getBase64EncodedString(String inputString) {
        try {
            String encodedString = new BASE64Encoder().encode(inputString.getBytes());
            LOGGER.info("4. Base Encoded String: {}", encodedString);
        } catch (Throwable e) {
            LOGGER.error(e.toString());
        }
    }
}
