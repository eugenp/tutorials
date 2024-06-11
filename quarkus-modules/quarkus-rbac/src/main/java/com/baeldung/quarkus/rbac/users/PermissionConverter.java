package com.baeldung.quarkus.rbac.users;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class PermissionConverter implements AttributeConverter<Set<Permission>, String> {

    @Override
    public String convertToDatabaseColumn(Set<Permission> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return attribute.stream()
                .map(Permission::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public Set<Permission> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return Set.of();
        }
        return Arrays.stream(dbData.split(","))
                .map(String::trim)
                .map(Permission::valueOf)
                .collect(Collectors.toSet());
    }
}
