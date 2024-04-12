package com.baeldung.multipletypesinmap;

import com.baeldung.collections.mulipletypesinmap.DynamicTypeValue;
import com.baeldung.collections.mulipletypesinmap.InstantTypeValue;
import com.baeldung.collections.mulipletypesinmap.IntArrayTypeValue;
import com.baeldung.collections.mulipletypesinmap.IntegerTypeValue;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class MultipleTypesInMapUnitTest {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        .withZone(ZoneId.systemDefault());

    private static final Integer intValue = 777;
    private static final int[] intArray = new int[]{2, 3, 5, 7, 11, 13};
    private static final Instant instant = Instant.now();

    private static final String KEY_INT = "E1 (Integer)";
    private static final String KEY_INT_ARRAY = "E2 (IntArray)";
    private static final String KEY_INSTANT = "E3 (Instant)";

    @Test
    void givenThreeTypes_whenUsingRawMap_thenPrintDescription() {
        Map<String, Object> rawMap = new HashMap<>();
        rawMap.put(KEY_INT, intValue);
        rawMap.put(KEY_INT_ARRAY, intArray);
        rawMap.put(KEY_INSTANT, instant);

        rawMap.forEach((k, v) -> {
            if (v instanceof Integer) {
                Integer theV = (Integer) v;
                String desc = String.format("The value is a %s integer: %d", theV > 0 ? "positive" : "negative", theV);
                System.out.println(k + " -> " + desc);
                assertThat(k).isEqualTo(KEY_INT);
                assertThat(desc).isEqualTo("The value is a positive integer: 777");
            } else if (v instanceof int[]) {
                int[] theV = (int[]) v;
                String desc = String.format("The value is an array of %d integers: %s", theV.length, Arrays.toString(theV));
                System.out.println(k + " -> " + desc);
                assertThat(k).isEqualTo(KEY_INT_ARRAY);
                assertThat(desc).isEqualTo("The value is an array of 6 integers: [2, 3, 5, 7, 11, 13]");
            } else if (v instanceof Instant) {
                Instant theV = (Instant) v;
                String desc = String.format("The value is an instant: %s", FORMATTER.format(theV));
                System.out.println(k + " -> " + desc);
                assertThat(k).isEqualTo(KEY_INSTANT);
                assertThat(desc).matches("^The value is an instant: \\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
            } else {
                throw new IllegalStateException("Unknown Type found.");
            }
        });
    }

    @Test
    void givenThreeTypes_whenUsingAnInterface_thenPrintDescription() {
        Map<String, DynamicTypeValue> theMap = new HashMap<>();
        theMap.put(KEY_INT, new IntegerTypeValue(intValue));
        theMap.put(KEY_INT_ARRAY, new IntArrayTypeValue(intArray));
        theMap.put(KEY_INSTANT, new InstantTypeValue(instant));

        theMap.forEach((k, v) -> System.out.println(k + " -> " + v.valueDescription()));

        assertThat(theMap.get(KEY_INT).valueDescription()).isEqualTo("The value is a positive integer: 777");
        assertThat(theMap.get(KEY_INT_ARRAY).valueDescription()).isEqualTo("The value is an array of 6 integers: [2, 3, 5, 7, 11, 13]");
        assertThat(theMap.get(KEY_INSTANT).valueDescription()).matches("^The value is an instant: \\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
    }
}
