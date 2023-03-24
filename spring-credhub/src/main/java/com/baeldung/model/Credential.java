package com.baeldung.model;

import java.util.Map;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Credential {
    Map<String, Object> value;
    String type;
    String name;
}
