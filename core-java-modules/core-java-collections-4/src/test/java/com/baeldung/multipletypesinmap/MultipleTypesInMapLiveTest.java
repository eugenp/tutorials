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

class MultipleTypesInMapLiveTest {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        .withZone(ZoneId.systemDefault());

    @Test
    void givenThreeTypes_whenUsingRawMap_thenPrintDescription() {

        Integer intValue = 777;
        int[] intArray = new int[]{2, 3, 5, 7, 11, 13};
        Instant instant = Instant.now();

        Map<String, Object> rawMap = new HashMap<>();
        rawMap.put("E1 (Integer)", intValue);
        rawMap.put("E2 (IntArray)", intArray);
        rawMap.put("E3 (Instant)", instant);

        rawMap.forEach((k, v) -> {
            if (v instanceof Integer) {
                Integer theV = (Integer) v;
                System.out.println(k + " -> " +
                    String.format("The value is a %s integer: %d.", theV > 0 ? "positive" : "negative", theV));
            } else if (v instanceof int[]) {
                int[] theV = (int[]) v;
                System.out.println(k + " -> " +
                    String.format("The value is an array of %d integers: %s", theV.length, Arrays.toString(theV)));
            } else if (v instanceof Instant) {
                Instant theV = (Instant) v;
                System.out.println(k + " -> " +
                    String.format("The value is an instant: %s", FORMATTER.format(theV)));
            } else {
                throw new IllegalStateException("Unknown Type found.");
            }
        });
    }

    @Test
    void givenThreeTypes_whenUsingAnInterface_thenPrintDescription() {

        Integer intValue = 777;
        int[] intArray = new int[]{2, 3, 5, 7, 11};
        Instant instant = Instant.now();

        Map<String, DynamicTypeValue> theMap = new HashMap<>();
        theMap.put("E1 (Integer)", new IntegerTypeValue(intValue));
        theMap.put("E2 (IntArray)", new IntArrayTypeValue(intArray));
        theMap.put("E3 (Instant)", new InstantTypeValue(instant));

        theMap.forEach((k, v) -> System.out.println(k + " -> " + v.valueDescription()));
    }
}
