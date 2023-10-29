package com.baeldung.xml.tohashmap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class XmlToHashMapUnitTest {

    private XmlToHashMap xmlToHashMap;
    private static final String TEST_XML_PATH = "src/main/resources/xml/xmltohashmap/test.xml";

    @BeforeEach
    void setUp(){
        xmlToHashMap = new XmlToHashMap();
    }

    @Test
    void whenUsingXstream_thenHashMapShouldBeCreated() throws IOException {
        Map<String, Employee> employeeMap = xmlToHashMap.xmlToHashMapUsingXstream(getXml());
        verify(employeeMap);
    }
    @Test
    void whenUsingUnderscore_thenHashMapShouldBeCreated() throws IOException {
        Map<String, Employee> employeeMap = xmlToHashMap.xmlToHashMapUsingUnderscore(getXml());
        verify(employeeMap);
    }

    @Test
    void whenUsingJackson_thenHashMapShouldBeCreated() throws IOException {
        Map<String, Employee> employeeMap = xmlToHashMap.xmlToHashMapUsingJackson(getXml());
        verify(employeeMap);
    }

    @Test
    void whenUsingJAXB_thenHashMapShouldBeCreated() throws IOException, JAXBException {
        Map<String, Employee> employeeMap = xmlToHashMap.xmlToHashMapUsingJAXB(getXml());
        verify(employeeMap);
    }

    @Test
    void whenUsingDOMXpath_thenHashMapShouldBeCreated() throws Exception {
        Map<String, Employee> employeeMap = xmlToHashMap.xmlToHashMapUsingDOMParserXpath(getXml());
        verify(employeeMap);
    }

    private void verify(Map<String,Employee> employeeMap){
        ArrayList<Employee> employees = new ArrayList<>(employeeMap.values());
        Assertions.assertEquals("654", employees.get(0).getId());
        Assertions.assertEquals("John", employees.get(0).getFirstName());
        Assertions.assertEquals("Doe", employees.get(0).getLastName());
        Assertions.assertEquals("776", employees.get(1).getId());
        Assertions.assertEquals("Steve", employees.get(1).getFirstName());
        Assertions.assertEquals("Smith", employees.get(1).getLastName());
    }

    private String getXml() throws IOException {
        return new String(Files.readAllBytes(Paths.get(TEST_XML_PATH)));
    }
}
