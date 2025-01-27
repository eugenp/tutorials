package com.baeldung.jackson.absentvsnull;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.baeldung.jackson.absentvsnull.model.Sample;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

class JacksonAbsentVsNullUnitTest {

    private static final String ID_FIELD = "id";
    private static final String PRIMITIVE_FIELD = "amount";
    private static final ObjectMapper DEFAULT_MAPPER = new ObjectMapper();

    private static final Sample ZERO_ARG_SAMPLE = new Sample();
    private static final Sample FULL_SAMPLE = new Sample();

    @AfterEach
    void setup() {
        List<String> keys = new ArrayList<>();
        keys.add("foo");
        keys.add("bar");

        List<Integer> values = new ArrayList<>();
        values.add(1);
        values.add(2);

        FULL_SAMPLE.setId(1l);
        FULL_SAMPLE.setName(ID_FIELD);
        FULL_SAMPLE.setAmount(3);
        FULL_SAMPLE.setKeys(keys);
        FULL_SAMPLE.setValues(values);
    }

    @Test
    final void whenSerializingWithDefaults_thenNullValuesIncluded() {
        Sample sample = ZERO_ARG_SAMPLE;
        Map<String, Serializable> map = DEFAULT_MAPPER.convertValue(sample, SampleUtils.MAP_TYPE);

        assertEquals(ZERO_ARG_SAMPLE.getAmount(), map.get("amount"));
        assertTrue(map.containsKey(ID_FIELD));
        assertNull(map.get(ID_FIELD));
    }

    @Test
    final void whenDeserializingToMapWithDefaults_thenAbsentFieldsAreIgnored() throws JsonProcessingException {
        String json = "{\"values\": [2], \"keys\": []}";
        Map<String, Serializable> map = DEFAULT_MAPPER.readValue(json, SampleUtils.MAP_TYPE);

        assertFalse(map.containsKey(PRIMITIVE_FIELD));
    }

    @Test
    final void whenDeserializingToMapWithDefaults_thenNullPrimitiveIsDefaulted() throws JsonProcessingException {
        String json = "{\"amount\": null}";
        Sample sample = DEFAULT_MAPPER.readValue(json, Sample.class);

        assertNotEquals(ZERO_ARG_SAMPLE.getAmount(), sample.getAmount());
    }

    @Test
    final void whenValidatingNullPrimitives_thenFailOnNullAmount() {
        String json = "{\"amount\": null}";

        assertThrows(MismatchedInputException.class, () -> SampleUtils.MAPPER
            .readValue(json, Sample.class));
    }

    @Test
    final void whenDeserializingToJavaWithDefaults_thenAbsentFieldsArePresent() throws JsonProcessingException {
        String json = "{\"values\": [2], \"keys\": []}";
        Sample sample = DEFAULT_MAPPER.readValue(json, Sample.class);

        assertEquals(ZERO_ARG_SAMPLE.getAmount(), sample.getAmount());
    }

    @Test
    final void whenSerializingNonDefault_thenOnlyNonJavaDefaultsIncluded() {
        final ObjectMapper mapper = SampleUtils.MAPPER;

        Sample sample = ZERO_ARG_SAMPLE;

        Map<String, Serializable> map = mapper.convertValue(sample, SampleUtils.MAP_TYPE);

        assertEquals(sample.getAmount(), map.get("amount"));
        assertFalse(map.containsKey(ID_FIELD));
    }

    @Test
    final void whenPatchingNonNulls_thenNullsIgnored() throws JsonProcessingException {
        List<Integer> values = new ArrayList<>();
        values.add(3);

        String json = String.format("{\"values\": %s}", values);

        SampleUtils.updateIgnoringNulls(json, FULL_SAMPLE);

        assertEquals(values, FULL_SAMPLE.getValues());
        assertNotNull(FULL_SAMPLE.getKeys());
    }

    @Test
    final void whenPatchingNonAbsent_thenNullsConsidered() throws JsonProcessingException {
        List<Integer> values = new ArrayList<>();
        values.add(3);

        String json = String.format("{\"values\": %s, \"keys\": null}", values);

        SampleUtils.updateNonAbsent(json, FULL_SAMPLE);

        assertEquals(values, FULL_SAMPLE.getValues());
        assertNull(FULL_SAMPLE.getKeys());
        assertNotNull(FULL_SAMPLE.getId());
    }
}
