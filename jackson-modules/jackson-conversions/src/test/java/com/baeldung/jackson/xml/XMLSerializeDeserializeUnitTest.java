package com.baeldung.jackson.xml;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class XMLSerializeDeserializeUnitTest {

    @Test
    public void whenJavaSerializedToXmlStr_thenCorrect() throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        String xml = xmlMapper.writeValueAsString(new SimpleBean());
        assertNotNull(xml);
    }

    @Test
    public void whenJavaSerializedToXmlFile_thenCorrect() throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writeValue(new File("target/simple_bean.xml"), new SimpleBean());
        File file = new File("target/simple_bean.xml");
        assertNotNull(file);
    }

    @Test
    public void whenJavaGotFromXmlStr_thenCorrect() throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        SimpleBean value = xmlMapper.readValue("<SimpleBean><x>1</x><y>2</y></SimpleBean>", SimpleBean.class);
        assertTrue(value.getX() == 1 && value.getY() == 2);
    }

    @Test
    public void whenJavaGotFromXmlFile_thenCorrect() throws IOException {
        File file = new File("src/test/resources/simple_bean.xml");
        XmlMapper xmlMapper = new XmlMapper();
        SimpleBean value = xmlMapper.readValue(file, SimpleBean.class);
        assertTrue(value.getX() == 1 && value.getY() == 2);
    }

    @Test
    public void whenJavaGotFromXmlStrWithCapitalElem_thenCorrect() throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        SimpleBeanForCapitalizedFields value = xmlMapper.readValue("<SimpleBeanForCapitalizedFields><X>1</X><y>2</y></SimpleBeanForCapitalizedFields>", SimpleBeanForCapitalizedFields.class);
        assertTrue(value.getX() == 1 && value.getY() == 2);
    }

    @Test
    public void whenJavaSerializedToXmlFileWithCapitalizedField_thenCorrect() throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writeValue(new File("target/simple_bean_capitalized.xml"), new SimpleBeanForCapitalizedFields());
        File file = new File("target/simple_bean_capitalized.xml");
        assertNotNull(file);
    }

    @Test
    public void whenJavaDeserializedFromXmlFile_thenCorrect() throws IOException {
        XmlMapper xmlMapper = new XmlMapper();

        String xml = "<person><firstName>Rohan</firstName><lastName>Daye</lastName><phoneNumbers><phoneNumbers>9911034731</phoneNumbers><phoneNumbers>9911033478</phoneNumbers></phoneNumbers><address><address><streetNumber>1</streetNumber><streetName>Name1</streetName><city>City1</city></address><address><streetNumber>2</streetNumber><streetName>Name2</streetName><city>City2</city></address></address></person>";
        Person value = xmlMapper.readValue(xml, Person.class);

        assertTrue(value.getAddress()
            .get(0)
            .getCity()
            .equalsIgnoreCase("city1")
            && value.getAddress()
                .get(1)
                .getCity()
                .equalsIgnoreCase("city2"));
    }

    @Test
    public void whenJavaSerializedToXmlFile_thenSuccess() throws IOException {
        XmlMapper xmlMapper = new XmlMapper();

        String expectedXml = "<person><firstName>Rohan</firstName><lastName>Daye</lastName><phoneNumbers><phoneNumbers>9911034731</phoneNumbers><phoneNumbers>9911033478</phoneNumbers></phoneNumbers><address><address><streetNumber>1</streetNumber><streetName>Name1</streetName><city>City1</city></address><address><streetNumber>2</streetNumber><streetName>Name2</streetName><city>City2</city></address></address></person>";

        Person person = new Person();

        person.setFirstName("Rohan");
        person.setLastName("Daye");

        List<String> ph = new ArrayList<>();
        ph.add("9911034731");
        ph.add("9911033478");
        person.setPhoneNumbers(ph);

        List<Address> addresses = new ArrayList<>();

        Address address1 = new Address();
        address1.setStreetNumber("1");
        address1.setStreetName("Name1");
        address1.setCity("City1");

        Address address2 = new Address();
        address2.setStreetNumber("2");
        address2.setStreetName("Name2");
        address2.setCity("City2");

        addresses.add(address1);
        addresses.add(address2);

        person.setAddress(addresses);

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        xmlMapper.writeValue(byteArrayOutputStream, person);
        assertEquals(expectedXml, byteArrayOutputStream.toString());
    }
}

class SimpleBean {
    private int x = 1;
    private int y = 2;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

}

class SimpleBeanForCapitalizedFields {
    @JsonProperty("X")
    private int x = 1;
    private int y = 2;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}