package com.baeldung.jaxb.test;

import com.baeldung.jaxb.Book;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JaxbTest {
    private Book book;
    private JAXBContext context;

    @Before
    public void before() throws JAXBException {
        book = new Book();
        book.setId(1L);
        book.setName("Book1");
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        book.setDate(new Date(1481909329718L));
        context = JAXBContext.newInstance(Book.class);
    }

    @Test
    public void marshal() throws JAXBException, IOException {
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        marshaller.marshal(book, new File("./book.xml"));
        String sampleBookXML = FileUtils.readFileToString(new File("./sample_book.xml"), "UTF-8");
        String marshallerBookXML = FileUtils.readFileToString(new File("./book.xml"), "UTF-8");

        Assert.assertEquals(sampleBookXML, marshallerBookXML);
    }

    @Test
    public void unMashal() throws JAXBException, IOException {
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Book unMarshallerbook = (Book) unmarshaller.unmarshal(new FileReader("./book.xml"));

        Assert.assertEquals(book, unMarshallerbook);
    }
}
