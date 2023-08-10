package com.baeldung.java9.prejpms;

import java.io.StringWriter;
import java.lang.StackWalker.Option;
import java.lang.StackWalker.StackFrame;
import java.util.Base64;
import java.util.concurrent.atomic.AtomicInteger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.sun.crypto.provider.SunJCE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
            AtomicInteger i = new AtomicInteger(0);
            StackWalker.getInstance((Option.RETAIN_CLASS_REFERENCE))
              .walk(s -> s.map(StackFrame::getDeclaringClass)
                .map(e -> {
                    i.getAndIncrement();
                    return e.getName();
                }))
              .forEach(name -> sbStack.append(String.format("%d. %s \n", i.get(), name)));
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
            String encodedString = new String(Base64.getEncoder()
              .encode(inputString.getBytes()));
            LOGGER.info("4. Base Encoded String: {}", encodedString);
        } catch (Throwable e) {
            LOGGER.error(e.toString());
        }
    }
}
