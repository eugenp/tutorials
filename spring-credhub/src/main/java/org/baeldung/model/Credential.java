package org.baeldung.model;

import java.util.Map;

import lombok.Getter;

@Getter
public class Credential {
    Map<String, Object> value;
    String type;
    String name;
}
