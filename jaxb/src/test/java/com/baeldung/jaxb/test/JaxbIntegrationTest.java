package com.baeldung.jaxb.test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.TimeZone;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.baeldung.jaxb.Book;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class JaxbIntegrationTest {
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
        marshaller.marshal(book, new File(this.getClass().getResource("/").getPath() + "/book.xml"));
        File sampleBookFile = new File(this.getClass().getResource("/sample_book.xml").getFile());
        File bookFile = new File(this.getClass().getResource("/book.xml").getFile());
        String sampleBookXML = FileUtils.readFileToString(sampleBookFile, "UTF-8");
        String marshallerBookXML = FileUtils.readFileToString(bookFile, "UTF-8");
        Assert.assertEquals(sampleBookXML.replace("\r", "").replace("\n", ""), marshallerBookXML.replace("\r", "").replace("\n", ""));
    }

    @Test
    public void unmarshal() throws JAXBException, IOException {
        Unmarshaller unmarshaller = context.createUnmarshaller();
        String bookFile = this.getClass().getResource("/book.xml").getFile();
        Book unMarshallerbook = (Book) unmarshaller.unmarshal(new FileReader(bookFile));
        Assert.assertEquals(book, unMarshallerbook);
    }
}
