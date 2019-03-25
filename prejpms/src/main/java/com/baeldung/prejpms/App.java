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

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) throws Exception {

        getCrytpographyProviderName();
        getCallStackClassNames();
        getXmlFromObject(new Book(100, "Java Modules Architecture"));
        getBase64EncodedString("Java");
    }

    private static void getCrytpographyProviderName() {
        try {
            logger.info("1. Java Cryptography Extension - Provider Name: " + new SunJCE().getName() + "\n");
        } catch (Throwable e) {
            logger.error(e.toString());
        }
    }

    private static void getCallStackClassNames() {
        try {
            int i = 0;
            StringBuffer sbStack = new StringBuffer();
            while (true) {
                Class<?> caller = Reflection.getCallerClass(i++);
                if (caller == null) {
                    break;
                } else {
                    sbStack.append(caller.getName())
                        .append("\n");
                }
            }
            logger.info("2. Call Stack Class Names:\n" + sbStack.toString());
        } catch (Throwable e) {
            logger.error(e.toString());
        }
    }

    private static void getXmlFromObject(Book book) {
        try {
            Marshaller marshallerObj = JAXBContext.newInstance(Book.class)
                .createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StringWriter sw = new StringWriter();
            marshallerObj.marshal(book, sw);
            logger.info("3. Xml for Book object:\n" + sw);
        } catch (Throwable e) {
            logger.error(e.toString());
        }

    }

    private static void getBase64EncodedString(String inputString) {
        try {
            String encodedString = new BASE64Encoder().encode(inputString.getBytes());
            logger.info("4. Base Encoded String: " + encodedString);
        } catch (Throwable e) {
            logger.error(e.toString());
        }
    }
}
