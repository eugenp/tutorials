package com.baeldung.webclient.json;

import com.baeldung.webclient.json.model.Address;
import com.baeldung.webclient.json.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UserConsumerServiceImpl implements UserConsumerService {

    private final WebClient webClient;
    private static final ObjectMapper mapper = new ObjectMapper();

    public UserConsumerServiceImpl(WebClient webClient) {
        this.webClient = webClient;
    }
    @Override
    public List<String> processUserDataFromObjectArray() {
        Mono<Object[]> response = webClient.get()
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(Object[].class).log();
        Object[] objects = response.block();
        return Arrays.stream(objects)
          .map(object -> mapper.convertValue(object, User.class))
          .map(User::getName)
          .collect(Collectors.toList());
    }

    @Override
    public List<String> processUserDataFromUserArray() {
        Mono<User[]> response =
          webClient.get()
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(User[].class).log();

        User[] userArray = response.block();
        return Arrays.stream(userArray)
          .map(User::getName)
          .collect(Collectors.toList());
    }

    @Override
    public List<String> processUserDataFromUserList() {
        Mono<List<User>> response = webClient.get()
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(new ParameterizedTypeReference<List<User>>() {});
        List<User> userList = response.block();

        return userList.stream()
          .map(User::getName)
          .collect(Collectors.toList());
    }

    @Override
    public List<String> processNestedUserDataFromUserArray() {
        Mono<User[]> response = webClient.get()
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(User[].class).log();
        User[] userArray = response.block();

        return Arrays.stream(userArray)
          .flatMap(user -> user.getAddressList().stream())
          .map(Address::getPostCode)
          .collect(Collectors.toList());
    }

    @Override
    public List<String> processNestedUserDataFromUserList() {
        Mono<List<User>> response = webClient.get()
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(new ParameterizedTypeReference<List<User>>() {});

        List<User> userList = response.block();
        return userList.stream()
          .flatMap(user -> user.getAddressList().stream())
          .map(Address::getPostCode)
          .collect(Collectors.toList());
    }
}