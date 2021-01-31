package com.baeldung.jackson.tocollection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class DeserializeToJavaCollectionUnitTest {
    private ObjectMapper objectMapper;
    private XmlMapper xmlMapper;
    private List<Book> expectedBookList;


    @BeforeEach
    void setup() {
        objectMapper = new ObjectMapper();
        xmlMapper = new XmlMapper();
        expectedBookList = Lists.newArrayList(
          new Book(1, "A Song of Ice and Fire", "George R. R. Martin"),
          new Book(2, "The Hitchhiker's Guide to the Galaxy", "Douglas Adams"),
          new Book(3, "Hackers And Painters", "Paul Graham"));
    }

    private String readFile(String path) {
        try (Scanner scanner = new Scanner(getClass().getResourceAsStream(path), "UTF-8")) {
            return scanner.useDelimiter("\\A").next();
        }
    }

    /*====================
     * JSON tests
     *====================
     */
    @Test
    void givenJsonString_whenDeserializingToList_thenThrowingClassCastException() throws JsonProcessingException {
        String jsonString = readFile("/to-java-collection/books.json");
        List<Book> bookList = objectMapper.readValue(jsonString, ArrayList.class);
        assertThat(bookList).size().isEqualTo(3);
        assertThatExceptionOfType(ClassCastException.class)
          .isThrownBy(() -> bookList.get(0).getBookId())
          .withMessageMatching(".*java.util.LinkedHashMap cannot be cast to .*com.baeldung.jackson.tocollection.Book.*");
    }

    @Test
    void givenJsonString_whenDeserializingWithTypeReference_thenGetExpectedList() throws JsonProcessingException {
        String jsonString = readFile("/to-java-collection/books.json");
        List<Book> bookList = objectMapper.readValue(jsonString, new TypeReference<List<Book>>() {});
        assertThat(bookList.get(0)).isInstanceOf(Book.class);
        assertThat(bookList).isEqualTo(expectedBookList);
    }

    @Test
    void givenJsonString_whenDeserializingWithJavaType_thenGetExpectedList() throws JsonProcessingException {
        String jsonString = readFile("/to-java-collection/books.json");
        CollectionType listType = objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Book.class);
        List<Book> bookList = objectMapper.readValue(jsonString, listType);
        assertThat(bookList.get(0)).isInstanceOf(Book.class);
        assertThat(bookList).isEqualTo(expectedBookList);
    }

    @Test
    void givenJsonString_whenDeserializingWithConvertValueAndTypeReference_thenGetExpectedList() throws JsonProcessingException {
        String jsonString = readFile("/to-java-collection/books.json");
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        List<Book> bookList = objectMapper.convertValue(jsonNode, new TypeReference<List<Book>>() {});
        assertThat(bookList.get(0)).isInstanceOf(Book.class);
        assertThat(bookList).isEqualTo(expectedBookList);
    }

    @Test
    void givenJsonString_whenDeserializingWithConvertValueAndJavaType_thenGetExpectedList() throws JsonProcessingException {
        String jsonString = readFile("/to-java-collection/books.json");
        JsonNode jsonNode = objectMapper.readTree(jsonString);
        List<Book> bookList = objectMapper.convertValue(jsonNode, objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Book.class));
        assertThat(bookList.get(0)).isInstanceOf(Book.class);
        assertThat(bookList).isEqualTo(expectedBookList);
    }

    /*====================
     * XML tests
     *====================
     */
    @Test
    void givenXml_whenDeserializingToList_thenThrowingClassCastException() throws JsonProcessingException {
        String xml = readFile("/to-java-collection/books.xml");
        List<Book> bookList = xmlMapper.readValue(xml, ArrayList.class);
        assertThat(bookList).size().isEqualTo(3);
        assertThatExceptionOfType(ClassCastException.class)
          .isThrownBy(() -> bookList.get(0).getBookId())
          .withMessageMatching(".*java.util.LinkedHashMap cannot be cast to .*com.baeldung.jackson.tocollection.Book.*");
    }

    @Test
    void givenXml_whenDeserializingWithTypeReference_thenGetExpectedList() throws JsonProcessingException {
        String xml = readFile("/to-java-collection/books.xml");
        List<Book> bookList = xmlMapper.readValue(xml, new TypeReference<List<Book>>() {});
        assertThat(bookList.get(0)).isInstanceOf(Book.class);
        assertThat(bookList).isEqualTo(expectedBookList);
    }

    @Test
    void givenXml_whenDeserializingWithConvertValueAndTypeReference_thenGetExpectedList() throws JsonProcessingException {
        String xml = readFile("/to-java-collection/books.xml");
        List node = xmlMapper.readValue(xml, List.class);
        List<Book> bookList = xmlMapper.convertValue(node, new TypeReference<List<Book>>() {});
        assertThat(bookList.get(0)).isInstanceOf(Book.class);
        assertThat(bookList).isEqualTo(expectedBookList);
    }

    @Test
    void givenXml_whenDeserializingWithConvertValueAndJavaType_thenGetExpectedList() throws JsonProcessingException {
        String xml = readFile("/to-java-collection/books.xml");
        List node = xmlMapper.readValue(xml, List.class);
        List<Book> bookList = xmlMapper.convertValue(node, objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, Book.class));
        assertThat(bookList.get(0)).isInstanceOf(Book.class);
        assertThat(bookList).isEqualTo(expectedBookList);
    }
}
