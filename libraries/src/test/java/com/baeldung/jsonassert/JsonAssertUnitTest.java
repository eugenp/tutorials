package com.baeldung.jsonassert;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.RegularExpressionValueMatcher;
import org.skyscreamer.jsonassert.comparator.ArraySizeComparator;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

import static org.assertj.core.api.Assertions.assertThat;

public class JsonAssertUnitTest {

    @Test
    public void givenLenientode_whenAssertEqualsSameJsonString_thenPass() throws JSONException {
        String actual = "{id:123,name:\"John\"}";
        JSONAssert.assertEquals("{id:123,name:\"John\"}", actual, JSONCompareMode.LENIENT);

        actual = "{id:123,name:\"John\",zip:\"33025\"}";
        JSONAssert.assertEquals("{id:123,name:\"John\"}", actual, JSONCompareMode.LENIENT);
    }

    @Test
    public void givenStrictMode_whenAssertNotEqualsExtendedJsonString_thenPass() throws JSONException {
        String actual = "{id:123,name:\"John\"}";
        JSONAssert.assertNotEquals("{name:\"John\"}", actual, JSONCompareMode.STRICT);
    }

    @Test
    public void whenUsingCompareModeOrBoolean_thenBothAreSame() throws JSONException {
        String actual = "{id:123,name:\"John\",zip:\"33025\"}";
        JSONAssert.assertEquals("{id:123,name:\"John\"}", actual, JSONCompareMode.LENIENT);
        JSONAssert.assertEquals("{id:123,name:\"John\"}", actual, false);

        actual = "{id:123,name:\"John\"}";
        JSONAssert.assertNotEquals("{name:\"John\"}", actual, JSONCompareMode.STRICT);
        JSONAssert.assertNotEquals("{name:\"John\"}", actual, true);
    }

    @Test
    public void givenDifferentOrderForJsonObject_whenAssertEquals_thenPass() throws JSONException {
        String result = "{id:1,name:\"John\"}";

        JSONAssert.assertEquals("{name:\"John\",id:1}", result, JSONCompareMode.STRICT);
        JSONAssert.assertEquals("{name:\"John\",id:1}", result, JSONCompareMode.LENIENT);
    }

    @Test
    public void givenDifferentTypes_whenAssertEqualsSameValue_thenPass() throws JSONException {
        JSONObject expected = new JSONObject();
        JSONObject actual = new JSONObject();
        expected.put("id", Integer.valueOf(12345));
        actual.put("id", Double.valueOf(12345));

        JSONAssert.assertEquals(expected, actual, false);
        JSONAssert.assertEquals(expected, actual, JSONCompareMode.LENIENT);
    }

    @Test
    public void givenNestedObjects_whenAssertEquals_thenPass() throws JSONException {
        String result = "{id:1,name:\"Juergen\", address:{city:\"Hollywood\", " + "state:\"LA\", zip:91601}}";
        JSONAssert.assertEquals("{id:1,name:\"Juergen\", address:{city:\"Hollywood\", " + "state:\"LA\", zip:91601}}", result, false);
    }

    @Test
    public void whenMessageUsedInAssertion_thenDisplayMessageOnFailure() throws JSONException {
        String actual = "{id:123,name:\"John\"}";
        String failureMessage = "Only one field is expected: name";
        try {
            JSONAssert.assertEquals(failureMessage, "{name:\"John\"}", actual, JSONCompareMode.STRICT);
        } catch (AssertionError ae) {
            assertThat(ae.getMessage()).containsIgnoringCase(failureMessage);
        }
    }

    @Test
    public void givenArray_whenComparing_thenOrderMustMatchForStrict() throws JSONException {
        String result = "[Alex, Barbera, Charlie, Xavier]";
        JSONAssert.assertEquals("[Charlie, Alex, Xavier, Barbera]", result, JSONCompareMode.LENIENT);
        JSONAssert.assertEquals("[Alex, Barbera, Charlie, Xavier]", result, JSONCompareMode.STRICT);
        JSONAssert.assertNotEquals("[Charlie, Alex, Xavier, Barbera]", result, JSONCompareMode.STRICT);
    }

    @Test
    public void givenArray_whenComparingExtended_thenNotEqual() throws JSONException {
        String result = "[1,2,3,4,5]";
        JSONAssert.assertEquals("[1,2,3,4,5]", result, JSONCompareMode.LENIENT);
        JSONAssert.assertNotEquals("[1,2,3]", result, JSONCompareMode.LENIENT);
        JSONAssert.assertNotEquals("[1,2,3,4,5,6]", result, JSONCompareMode.LENIENT);
    }

    @Test
    public void whenComparingSizeOfArray_thenPass() throws JSONException {
        String names = "{names:[Alex, Barbera, Charlie, Xavier]}";
        JSONAssert.assertEquals("{names:[4]}", names, new ArraySizeComparator(JSONCompareMode.LENIENT));
    }

    @Test
    public void whenComparingContentsOfArray_thenPass() throws JSONException {
        String ratings = "{ratings:[3.2,3.5,4.1,5,1]}";
        JSONAssert.assertEquals("{ratings:[1,5]}", ratings, new ArraySizeComparator(JSONCompareMode.LENIENT));
    }

    @Test
    public void givenValueMatcher_whenComparingUsingRegex_thenPass() throws IllegalArgumentException, JSONException {
        JSONAssert.assertEquals("{entry:{id:x}}", "{entry:{id:1, id:2}}", new CustomComparator(JSONCompareMode.STRICT, new Customization("entry.id", new RegularExpressionValueMatcher<Object>("\\d"))));

        JSONAssert.assertNotEquals("{entry:{id:x}}", "{entry:{id:1, id:as}}", new CustomComparator(JSONCompareMode.STRICT, new Customization("entry.id", new RegularExpressionValueMatcher<Object>("\\d"))));
    }
}
