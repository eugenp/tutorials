package com.baeldung.xml.xmltohashmap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;

import javax.xml.bind.JAXBException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class XmlToHashmapUnitTest {

    private XmlToHashmap xmlToHashmap;
    private static final String TEST_XML_PATH = "src/main/resources/xml/xmltohashmap/test.xml";

    @BeforeEach
    void setUp(){
        xmlToHashmap = new XmlToHashmap();
    }

    @Test
    void whenUsingXstream_thenHashmapShouldBeCreated() throws IOException {
        Map<String, Employee> employeeMap = xmlToHashmap.xmlToHashmapUsingXstream(getXml());
        verify(employeeMap);
    }
    @Test
    void
    whenUsingUnderscore_thenHashmapShouldBeCreated() throws IOException {
        Map<String, Employee> employeeMap = xmlToHashmap.xmlToHashmapUsingUnderscore(getXml());
        verify(employeeMap);
    }

    @Test
    void whenUsingJackson_thenHashmapShouldBeCreated() throws IOException {
        Map<String, Employee> employeeMap = xmlToHashmap.xmlToHashmapUsingJackson(getXml());
        verify(employeeMap);
    }

    @Test
    void whenUsingJAXB_thenHashmapShouldBeCreated() throws IOException, JAXBException {
        Map<String, Employee> employeeMap = xmlToHashmap.xmlToHashmapUsingJAXB(getXml());
        verify(employeeMap);
    }

    @Test
    void whenUsingDOMXpath_thenHashmapShouldBeCreated() throws Exception {
        Map<String, Employee> employeeMap = xmlToHashmap.xmlToHashmapUsingDOMParserXpath(getXml());
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
        return new String(Files.readAllBytes(Paths.get(XmlToHashmapUnitTest.TEST_XML_PATH)));
    }
}
