package com.baeldung.jackson.absentvsnull;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.baeldung.jackson.absentvsnull.model.Sample;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SampleUtils {

    public static final TypeReference<HashMap<String, Serializable>> MAP_TYPE = new TypeReference<HashMap<String, Serializable>>() {
    };
    public static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        MAPPER.enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);

        MAPPER.setSerializationInclusion(Include.NON_NULL);
        MAPPER.setSerializationInclusion(Include.NON_ABSENT);
        MAPPER.setSerializationInclusion(Include.NON_DEFAULT);
    }

    private SampleUtils() {
    }

    public static Sample deserialize(String json) throws JsonProcessingException {
        return MAPPER.readValue(json, Sample.class);
    }

    public static Map<String, Serializable> deserializeAsMap(String json) throws JsonProcessingException {
        return MAPPER.readValue(json, MAP_TYPE);
    }

    public static void updateConsideringNulls(String json, Sample current) throws JsonProcessingException {
        Sample update = deserialize(json);

        current.setId(update.getId());
        current.setName(update.getName());
        current.setAmount(update.getAmount());

        current.setKeys(update.getKeys());
        current.setValues(update.getValues());
    }

    public static void updateIgnoringNulls(String json, Sample current) throws JsonProcessingException {
        Sample update = deserialize(json);

        ifNotNull(current::setId, update::getId);
        ifNotNull(current::setName, update::getName);
        ifNotNull(current::setAmount, update::getAmount);

        ifListNotNull(current::setKeys, update::getKeys);
        ifListNotNull(current::setValues, update::getValues);
    }

    @SuppressWarnings("unchecked")
    public static void updateNonAbsent(String json, Sample current) throws JsonProcessingException {
        Map<String, Serializable> update = deserializeAsMap(json);

        if (update.containsKey("id")) {
            current.setId((Long) update.get("id"));
        }

        if (update.containsKey("name")) {
            current.setName((String) update.get("name"));
        }

        if (update.containsKey("amount")) {
            current.setAmount((int) update.get("amount"));
        }

        if (update.containsKey("keys")) {
            current.setKeys((List<String>) update.get("keys"));
        }

        if (update.containsKey("values")) {
            current.setValues((List<Integer>) update.get("values"));
        }
    }

    private static <T extends Serializable> void ifNotNull(Consumer<T> setter, Supplier<T> supplier) {
        if (supplier != null && supplier.get() != null) {
            setter.accept(supplier.get());
        }
    }

    private static <T extends Serializable> void ifListNotNull(Consumer<List<T>> setter, Supplier<List<T>> supplier) {
        if (supplier != null && supplier.get() != null) {
            setter.accept(supplier.get());
        }
    }
}
