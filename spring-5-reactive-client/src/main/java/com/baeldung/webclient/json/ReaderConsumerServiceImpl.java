package com.baeldung.webclient.json;

import com.baeldung.webclient.json.model.Book;
import com.baeldung.webclient.json.model.Reader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ReaderConsumerServiceImpl implements ReaderConsumerService {

    private final WebClient webClient;
    private static final ObjectMapper mapper = new ObjectMapper();

    public ReaderConsumerServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }
    @Override
    public List<String> processReaderDataFromObjectArray() {
        Mono<Object[]> response = webClient.get()
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(Object[].class).log();
        Object[] objects = response.block();
        return Arrays.stream(objects)
          .map(object -> mapper.convertValue(object, Reader.class))
          .map(Reader::getName)
          .collect(Collectors.toList());
    }

    @Override
    public List<String> processReaderDataFromReaderArray() {
        Mono<Reader[]> response =
          webClient.get()
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(Reader[].class).log();

        Reader[] readers = response.block();
        return Arrays.stream(readers)
          .map(Reader::getName)
          .collect(Collectors.toList());
    }

    @Override
    public List<String> processReaderDataFromReaderList() {
        Mono<List<Reader>> response = webClient.get()
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(new ParameterizedTypeReference<List<Reader>>() {});
        List<Reader> readers = response.block();

        return readers.stream()
          .map(Reader::getName)
          .collect(Collectors.toList());
    }

    @Override
    public List<String> processNestedReaderDataFromReaderArray() {
        Mono<Reader[]> response = webClient.get()
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(Reader[].class).log();
        Reader[] readers = response.block();

        return Arrays.stream(readers)
          .flatMap(reader -> reader.getFavouriteBooks().stream())
          .map(Book::getAuthor)
          .collect(Collectors.toList());
    }

    @Override
    public List<String> processNestedReaderDataFromReaderList() {
        Mono<List<Reader>> response = webClient.get()
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(new ParameterizedTypeReference<List<Reader>>() {});

        List<Reader> readers = response.block();
        return readers.stream()
          .flatMap(reader -> reader.getFavouriteBooks().stream())
          .map(Book::getAuthor)
          .collect(Collectors.toList());
    }
}