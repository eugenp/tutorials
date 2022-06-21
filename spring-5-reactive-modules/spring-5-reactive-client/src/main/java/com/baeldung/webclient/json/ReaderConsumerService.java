package com.baeldung.webclient.json;

import com.baeldung.webclient.json.model.Book;

import java.util.List;

public interface ReaderConsumerService {

    List<Book> processReaderDataFromObjectArray();

    List<Book> processReaderDataFromReaderArray();

    List<Book> processReaderDataFromReaderList();

    List<String> processNestedReaderDataFromReaderArray();

    List<String> processNestedReaderDataFromReaderList();
}
