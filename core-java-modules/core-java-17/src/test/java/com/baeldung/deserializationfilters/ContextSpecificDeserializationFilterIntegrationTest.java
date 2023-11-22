package com.baeldung.deserializationfilters;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputFilter;
import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.baeldung.deserializationfilters.pojo.ContextSpecific;
import com.baeldung.deserializationfilters.pojo.NestedSample;
import com.baeldung.deserializationfilters.pojo.Sample;
import com.baeldung.deserializationfilters.pojo.SampleExploit;
import com.baeldung.deserializationfilters.service.LimitedArrayService;
import com.baeldung.deserializationfilters.service.LowDepthService;
import com.baeldung.deserializationfilters.service.SmallObjectService;
import com.baeldung.deserializationfilters.utils.DeserializationUtils;
import com.baeldung.deserializationfilters.utils.FilterUtils;
import com.baeldung.deserializationfilters.utils.SerializationUtils;

public class ContextSpecificDeserializationFilterIntegrationTest {

    private static ByteArrayOutputStream serialSampleA = new ByteArrayOutputStream();
    private static ByteArrayOutputStream serialBigSampleA = new ByteArrayOutputStream();

    private static ByteArrayOutputStream serialSampleB = new ByteArrayOutputStream();
    private static ByteArrayOutputStream serialBigSampleB = new ByteArrayOutputStream();

    private static ByteArrayOutputStream serialSampleC = new ByteArrayOutputStream();
    private static ByteArrayOutputStream serialBigSampleC = new ByteArrayOutputStream();

    private static ByteArrayInputStream bytes(ByteArrayOutputStream stream) {
        return new ByteArrayInputStream(stream.toByteArray());
    }

    @BeforeAll
    static void setup() throws IOException {
        ObjectInputFilter.Config.setSerialFilterFactory(new ContextSpecificDeserializationFilterFactory());

        SerializationUtils.serialize(new Sample("simple"), serialSampleA);
        SerializationUtils.serialize(new SampleExploit(), serialBigSampleA);

        SerializationUtils.serialize(new Sample(new int[] { 1, 2, 3 }), serialSampleB);
        SerializationUtils.serialize(new Sample(new int[] { 1, 2, 3, 4, 5, 6 }), serialBigSampleB);

        SerializationUtils.serialize(new Sample(new NestedSample(null)), serialSampleC);
        SerializationUtils.serialize(new Sample(new NestedSample(new Sample("deep"))), serialBigSampleC);
    }

    @Test
    void whenSmallObjectContext_thenCorrectFilterApplied() {
        Set<ContextSpecific> result = new SmallObjectService().process( //
            bytes(serialSampleA), //
            bytes(serialBigSampleA));

        assertEquals(1, result.size());
        assertEquals("simple", ((Sample) result.iterator()
            .next()).getName());
    }

    @Test
    void whenLimitedArrayContext_thenCorrectFilterApplied() {
        Set<ContextSpecific> result = new LimitedArrayService().process( //
            bytes(serialSampleB), //
            bytes(serialBigSampleB));

        assertEquals(1, result.size());
    }

    @Test
    void whenLowDepthContext_thenCorrectFilterApplied() {
        Set<ContextSpecific> result = new LowDepthService().process( //
            bytes(serialSampleC), //
            bytes(serialBigSampleC));

        assertEquals(1, result.size());
    }

    @Test
    void givenExtraFilter_whenCombinedContext_thenMergedFiltersApplied() {
        Set<ContextSpecific> result = new LowDepthService().process( //
            FilterUtils.safeSizeFilter(190), //
            bytes(serialSampleA), //
            bytes(serialBigSampleA), //
            bytes(serialSampleC), //
            bytes(serialBigSampleC));

        assertEquals(1, result.size());
        assertEquals("simple", ((Sample) result.iterator()
            .next()).getName());
    }

    @Test
    void givenFallbackContext_whenUsingBaseClasses_thenRestrictiveFilterApplied() throws IOException {
        String a = new String("a");
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        SerializationUtils.serialize(a, outStream);

        String deserializedA = (String) DeserializationUtils.deserialize(bytes(outStream));

        assertEquals(a, deserializedA);
    }

    @Test
    void givenFallbackContext_whenUsingAppClasses_thenRejected() throws IOException {
        Sample a = new Sample("a");
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        SerializationUtils.serialize(a, outStream);

        Sample deserializedA = (Sample) DeserializationUtils.deserialize(bytes(outStream));

        assertNull(deserializedA);
    }
}
