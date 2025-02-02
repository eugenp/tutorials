package com.baeldung.jackson.absentvsnull;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

class JacksonAbsentVsNullUnitTest {

    static final TypeReference<Map<String, Serializable>> MAP_TYPE = new TypeReference<Map<String, Serializable>>() {
    };

    static class Sample {

        private Long id;
        private String name;
        private int amount = 1;
        private List<String> keys;
        private List<Integer> values;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public List<String> getKeys() {
            return keys;
        }

        public void setKeys(List<String> names) {
            this.keys = names;
        }

        public List<Integer> getValues() {
            return values;
        }

        public void setValues(List<Integer> values) {
            this.values = values;
        }

        static Sample basic() {
            Sample defaults = new Sample();

            List<String> keys = List.of("foo", "bar");
            List<Integer> values = List.of(1, 2);

            defaults.setId(1l);
            defaults.setName("name");
            defaults.setAmount(3);
            defaults.setKeys(keys);
            defaults.setValues(values);

            return defaults;
        }
    }

    static void updateIgnoringNulls(String json, Sample current) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Sample update = mapper.readValue(json, Sample.class);
        mapper.setSerializationInclusion(Include.NON_DEFAULT);

        if (update.getId() != null) {
            current.setId(update.getId());
        }

        if (update.getName() != null) {
            current.setName(update.getName());
        }

        current.setAmount(update.getAmount());

        if (update.getKeys() != null) {
            current.setKeys(update.getKeys());
        }

        if (update.getValues() != null) {
            current.setValues(update.getValues());
        }
    }

    @SuppressWarnings("unchecked")
    static void updateNonAbsent(String json, Sample current) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Serializable> update = mapper.readValue(json, MAP_TYPE);
        mapper.setSerializationInclusion(Include.NON_DEFAULT);

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

    @Test
    void whenSerializingWithDefaults_thenNullValuesIncluded() {
        Sample zeroArg = new Sample();
        Map<String, Serializable> map = new ObjectMapper().convertValue(zeroArg, MAP_TYPE);

        assertEquals(zeroArg.getAmount(), map.get("amount"));
        assertTrue(map.containsKey("id"));
        assertNull(map.get("id"));
    }

    @Test
    void whenDeserializingToMapWithDefaults_thenAbsentFieldsAreIgnored() throws JsonProcessingException {
        String json = """
            {
              "values": [2],
              "keys": []}
            """;
        Map<String, Serializable> map = new ObjectMapper().readValue(json, MAP_TYPE);

        Set<String> keySet = map.keySet();
        assertEquals(2, keySet.size());
        assertTrue(keySet.containsAll(List.of("keys", "values")));
    }

    @Test
    void whenDeserializingToMapWithDefaults_thenNullPrimitiveIsDefaulted() throws JsonProcessingException {
        String json = """
            {
              "amount": null
            }
            """;
        Sample sample = new ObjectMapper().readValue(json, Sample.class);

        assertEquals(0, sample.getAmount());
    }

    @Test
    void whenValidatingNullPrimitives_thenFailOnNullAmount() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES);

        String json = """
            {
              "amount": null
            }
            """;

        assertThrows(MismatchedInputException.class, () -> mapper.readValue(json, Sample.class));
    }

    @Test
    void whenDeserializingToJavaWithDefaults_thenAbsentFieldsArePresent() throws JsonProcessingException {
        String json = """
            {
              "values": [2],
              "keys": []
            }
            """;
        Sample sample = new ObjectMapper().readValue(json, Sample.class);

        assertEquals(new Sample().getAmount(), sample.getAmount());
    }

    @Test
    void whenSerializingNonDefault_thenOnlyNonJavaDefaultsIncluded() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_DEFAULT);

        Sample zeroArg = new Sample();

        Map<String, Serializable> map = mapper.convertValue(zeroArg, MAP_TYPE);

        assertEquals(zeroArg.getAmount(), map.get("amount"));
        assertEquals(1, map.keySet()
            .size());
    }

    @Test
    void whenPatchingNonNulls_thenNullsIgnored() throws JsonProcessingException {
        List<Integer> values = List.of(3);

        Sample defaults = Sample.basic();

        String json = """
            {
              "values": %s
            }
            """.formatted(values);

        updateIgnoringNulls(json, defaults);

        assertEquals(values, defaults.getValues());
        assertNotNull(defaults.getKeys());
    }

    @Test
    void whenPatchingNonAbsent_thenNullsConsidered() throws JsonProcessingException {
        List<Integer> values = List.of(3);

        Sample defaults = Sample.basic();

        String json = """
            {
              "values": %s,
              "keys": null
            }
            """.formatted(values);

        updateNonAbsent(json, defaults);

        assertEquals(values, defaults.getValues());
        assertNull(defaults.getKeys());
        assertNotNull(defaults.getId());
    }
}
