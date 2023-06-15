package com.baeldung.xml.json2xml;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.github.underscore.U;
import org.json.JSONObject;
import org.json.XML;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class JsonToXmlUnitTest {

    @Test
    public void givenJsonString_whenConvertToXMLUsingJsonJava_thenConverted() {
        String jsonString = "{\"name\":\"John\", \"age\":20, \"address\":{\"street\":\"Wall Street\", \"city\":\"New York\"}}";
        JSONObject jsonObject = new JSONObject(jsonString);
        String xmlString = XML.toString(jsonObject);
        Assertions.assertEquals("<address><city>New York</city><street>Wall Street</street></address><name>John</name><age>20</age>", xmlString);
    }

    @Test
    public void givenJsonString_whenConvertToXMLUsingJackson_thenConverted() throws JsonProcessingException {
        String jsonString = "{\"name\":\"John\", \"age\":20, \"address\":{\"street\":\"Wall Street\", \"city\":\"New York\"}}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        String xmlString = new XmlMapper().writeValueAsString(jsonNode);
        Assertions.assertEquals("<ObjectNode><name>John</name><age>20</age><address><street>Wall Street</street><city>New York</city></address></ObjectNode>", xmlString);
    }

    @Test
    public void givenJsonString_whenConvertToXMLUsingJacksonWithXMLDeclarationAndRoot_thenConverted() throws JsonProcessingException {
        String jsonString = "{\"name\":\"John\", \"age\":20, \"address\":{\"street\":\"Wall Street\", \"city\":\"New York\"}}";
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
        xmlMapper.configure(ToXmlGenerator.Feature.WRITE_XML_1_1, true);
        String xmlString = xmlMapper.writer().withRootName("root").withDefaultPrettyPrinter().writeValueAsString(jsonNode);
        Assertions.assertEquals("<?xml version='1.1' encoding='UTF-8'?>\n" +
                "<root>\n" +
                "  <name>John</name>\n" +
                "  <age>20</age>\n" +
                "  <address>\n" +
                "    <street>Wall Street</street>\n" +
                "    <city>New York</city>\n" +
                "  </address>\n" +
                "</root>\n", xmlString);
    }

    @Test
    public void givenJsonString_whenConvertToXMLUsingUnderscoreJava_thenConverted() {
        String jsonString = "{\"name\":\"John\", \"age\":20}";
        String xmlString = U.jsonToXml(jsonString);
        Assertions.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<root>\n" +
                "  <name>John</name>\n" +
                "  <age number=\"true\">20</age>\n" +
                "</root>", xmlString);
    }

    @Test
    public void givenJsonString_whenConvertToXMLUsingUnderscoreJavaWithoutAttributes_thenConverted() {
        String jsonString = "{\"name\":\"John\", \"age\":20}";
        String xmlString = U.jsonToXml(jsonString, U.JsonToXmlMode.REMOVE_ATTRIBUTES);
        Assertions.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<root>\n" +
                "  <name>John</name>\n" +
                "  <age>20</age>\n" +
                "</root>", xmlString);
    }
}

