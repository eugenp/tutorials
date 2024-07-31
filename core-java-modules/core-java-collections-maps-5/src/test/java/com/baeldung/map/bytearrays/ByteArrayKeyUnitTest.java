package com.baeldung.map.bytearrays;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ByteArrayKeyUnitTest {
    @Test
    void givenPrimitiveByteArrayKey_whenRetrievingFromMap_shouldRetrieveDifferentObjects() {
        // given
        byte[] key1 = {1, 2, 3};
        byte[] key2 = {1, 2, 3};
        String value1 = "value1";
        String value2 = "value2";
        Map<byte[], String> map = new HashMap<>();
        map.put(key1, value1);
        map.put(key2, value2);

        // when
        String retrievedValue1 = map.get(key1);
        String retrievedValue2 = map.get(key2);
        String retrievedValue3 = map.get(new byte[]{1, 2, 3});

        // then
        assertThat(retrievedValue1).isEqualTo(value1);
        assertThat(retrievedValue2).isEqualTo(value2);
        assertThat(retrievedValue3).isNull();
    }

    @Test
    void givenEncodedStringKey_whenRetrievingFromMap_shouldRetrieveLastPutObject() {
        // given
        String key1 = Base64.getEncoder().encodeToString(new byte[]{1, 2, 3});
        String key2 = Base64.getEncoder().encodeToString(new byte[]{1, 2, 3});
        String value1 = "value1";
        String value2 = "value2";
        Map<String, String> map = new HashMap<>();
        map.put(key1, value1);
        map.put(key2, value2);

        // when
        String retrievedValue1 = map.get(key1);
        String retrievedValue2 = map.get(key2);

        // then
        assertThat(key1).isEqualTo(key2);
        assertThat(retrievedValue1).isEqualTo(value2);
        assertThat(retrievedValue2).isEqualTo(value2);
        assertThat(retrievedValue1).isEqualTo(retrievedValue2);
    }

    @Test
    void givenByteListKey_whenRetrievingFromMap_shouldRetrieveLastPutObject() {
        // given
        List<Byte> key1 = ImmutableList.of((byte)1, (byte)2, (byte)3);
        List<Byte> key2 = ImmutableList.of((byte)1, (byte)2, (byte)3);
        String value1 = "value1";
        String value2 = "value2";
        Map<List<Byte>, String> map = new HashMap<>();
        map.put(key1, value1);
        map.put(key2, value2);

        // when
        String retrievedValue1 = map.get(key1);
        String retrievedValue2 = map.get(key2);

        // then
        assertThat(key1).isEqualTo(key2);
        assertThat(retrievedValue1).isEqualTo(value2);
        assertThat(retrievedValue2).isEqualTo(value2);
        assertThat(retrievedValue1).isEqualTo(retrievedValue2);
    }

    @Test
    void givenCustomWrapperKey_whenRetrievingFromMap_shouldRetrieveLastPutObject() {
        // given
        BytesKey key1 = new BytesKey(new byte[]{1, 2, 3});
        BytesKey key2 = new BytesKey(new byte[]{1, 2, 3});
        String value1 = "value1";
        String value2 = "value2";
        Map<BytesKey, String> map = new HashMap<>();
        map.put(key1, value1);
        map.put(key2, value2);

        // when
        String retrievedValue1 = map.get(key1);
        String retrievedValue2 = map.get(key2);
        String retrievedValue3 = map.get(new BytesKey(new byte[]{1, 2, 3}));

        // then
        assertThat(key1).isEqualTo(key2);
        assertThat(retrievedValue1).isEqualTo(value2);
        assertThat(retrievedValue2).isEqualTo(value2);
        assertThat(retrievedValue1).isEqualTo(retrievedValue2);
        assertThat(retrievedValue3).isEqualTo(value2);

    }
}
