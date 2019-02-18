package com.baeldung.jackson.xml;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.baeldung.jackson.dtos.Address;
import com.baeldung.jackson.dtos.Person;
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
        String xml = inputStreamToString(new FileInputStream(file));
        SimpleBean value = xmlMapper.readValue(xml, SimpleBean.class);
        assertTrue(value.getX() == 1 && value.getY() == 2);
    }

    @Test
    public void whenJavaGotFromXmlStrWithCapitalElem_thenCorrect() throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        SimpleBeanForCapitalizedFields value = xmlMapper.
            readValue("<SimpleBeanForCapitalizedFields><X>1</X><y>2</y></SimpleBeanForCapitalizedFields>",
                SimpleBeanForCapitalizedFields.class);
        assertTrue(value.getX() == 1 && value.getY() == 2);
    }

    @Test
    public void whenJavaSerializedToXmlFileWithCapitalizedField_thenCorrect() throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.writeValue(new File("target/simple_bean_capitalized.xml"),
            new SimpleBeanForCapitalizedFields());
        File file = new File("target/simple_bean_capitalized.xml");
        assertNotNull(file);
    }

    @Test
    public void whenJavaDeserializedFromXmlFile_thenCorrect() throws IOException {
        XmlMapper xmlMapper = new XmlMapper();
        Person value = xmlMapper.readValue(new File("src/test/resources/person.xml"), Person.class);

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

        Person person = new Person();

        person.setFirstName("Rohan");
        person.setLastName("Daye");

        List<String> ph = new ArrayList<>();
        ph.add("9911778981");
        ph.add("9991111111");
        person.setPhoneNumbers(ph);

        List<Address> addresses = new ArrayList<>();
        
        Address address1 = new Address();
        address1.setStreetNumber("1");
        address1.setStreetName("streetname1");
        address1.setCity("city1");
        
        Address address2 = new Address();
        address2.setStreetNumber("2");
        address2.setStreetName("streetname2");
        address2.setCity("city2");
        
        addresses.add(address1);
        addresses.add(address2);
        person.setAddress(addresses);

        xmlMapper.writeValue(new File("src/test/resources/PersonGenerated.xml"), person);
    }

    private static String inputStreamToString(InputStream is) throws IOException {
        BufferedReader br;
        StringBuilder sb = new StringBuilder();

        String line;
        br = new BufferedReader(new InputStreamReader(is));
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
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