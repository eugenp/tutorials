package com.baeldung.resttemplate.json.consumer.service;

import java.util.List;

public interface UserConsumerService {

    Object[] getUsersAsObjectArray();

    List<String> processUserDataFromUserArray();

    List<String> processUserDataFromUserList();

    List<String> processNestedUserDataFromUserArray();

    List<String> processNestedUserDataFromUserList();
}
