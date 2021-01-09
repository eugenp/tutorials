package com.baeldung.webclient.json;

import java.util.List;

public interface UserConsumerService {

    List<String> processUserDataFromObjectArray();

    List<String> processUserDataFromUserArray();

    List<String> processUserDataFromUserList();

    List<String> processNestedUserDataFromUserArray();

    List<String> processNestedUserDataFromUserList();
}
