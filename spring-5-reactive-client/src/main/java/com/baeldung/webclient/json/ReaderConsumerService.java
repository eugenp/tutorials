package com.baeldung.webclient.json;

import java.util.List;

public interface ReaderConsumerService {

    List<String> processReaderDataFromObjectArray();

    List<String> processReaderDataFromReaderArray();

    List<String> processReaderDataFromReaderList();

    List<String> processNestedReaderDataFromReaderArray();

    List<String> processNestedReaderDataFromReaderList();
}
