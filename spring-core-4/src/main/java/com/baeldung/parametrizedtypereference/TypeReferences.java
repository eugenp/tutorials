package com.baeldung.parametrizedtypereference;

import org.springframework.core.ParameterizedTypeReference;

import java.util.List;
import java.util.Map;

public class TypeReferences {
    public static final ParameterizedTypeReference<List<User>> USER_LIST =
            new ParameterizedTypeReference<List<User>>() {};

    public static final ParameterizedTypeReference<Map<String, List<User>>> USER_MAP =
            new ParameterizedTypeReference<Map<String, List<User>>>() {};

}
