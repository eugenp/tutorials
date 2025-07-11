package com.baeldung.stringmap;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Map;

@Service
public class MapValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Map.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Map<?, ?> rawMap = (Map<?, ?>) target;

        for (Map.Entry<?, ?> entry : rawMap.entrySet()) {
            Object rawKey = entry.getKey();
            Object rawValue = entry.getValue();

            if (!(rawKey instanceof String) || !(rawValue instanceof String)) {
                errors.rejectValue("map[" + rawKey + "]", "map.entry.invalidType", "Map must contain only String keys and values");
                continue;
            }

            String key = (String) rawKey;
            String value = (String) rawValue;

            // Key validation
            if (key.length() < 10) {
                errors.rejectValue("map[" + key + "]", "key.tooShort", "Key must be at least 10 characters long");
            }

            // Value validation
            if (!StringUtils.hasText(value)) {
                errors.rejectValue("map[" + key + "]", "value.blank", "Value must not be blank");
            }
        }
    }
}


