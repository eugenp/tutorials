package com.baeldung.jackson.tocollection;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class JsonToCollectionUtilUnitTest {

    private List<Book> expectedBookList;


    @BeforeEach
    void setup() {
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

    @Test
    void givenJsonString_whenCalljsonArrayToList_thenGetExpectedList() throws IOException {
        String jsonString = readFile("/to-java-collection/books.json");
        List<Book> bookList = JsonToCollectionUtil.jsonArrayToList(jsonString, Book.class);
        assertThat(bookList.get(0)).isInstanceOf(Book.class);
        assertThat(bookList).isEqualTo(expectedBookList);
    }

    @Test
    void givenJsonString_whenCalljsonArrayToList2_thenGetException() throws IOException {
        String jsonString = readFile("/to-java-collection/books.json");
        List<Book> bookList = JsonToCollectionUtil.jsonArrayToList2(jsonString, Book.class);
        assertThat(bookList).size().isEqualTo(3);
        assertThatExceptionOfType(ClassCastException.class)
          .isThrownBy(() -> bookList.get(0).getBookId())
          .withMessageMatching(".*java.util.LinkedHashMap cannot be cast to .*com.baeldung.jackson.tocollection.Book.*");
    }

}
