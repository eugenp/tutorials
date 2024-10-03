package com.baeldung.jsoncollectioncomparison;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static org.hamcrest.MatcherAssert.assertThat;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs;

public class JsonCollectionComparisonUnitTest {
    String jsonArray1 = "["
            + "{\"id\": 1, \"name\": \"Alice\", \"address\": {\"city\": \"NY\", \"street\": \"5th Ave\"}}, "
            + "{\"id\": 2, \"name\": \"Bob\", \"address\": {\"city\": \"LA\", \"street\": \"Sunset Blvd\"}}"
            + "]";

    String jsonArray2 = "["
            + "{\"id\": 2, \"name\": \"Bob\", \"address\": {\"city\": \"LA\", \"street\": \"Sunset Blvd\"}}, "
            + "{\"id\": 1, \"name\": \"Alice\", \"address\": {\"city\": \"NY\", \"street\": \"5th Ave\"}}"
            + "]";

    @Test
    public void givenJsonArrays_whenUsingJSONAssertIgnoringOrder_thenEqual() throws JSONException {
        JSONAssert.assertEquals(jsonArray1, jsonArray2, JSONCompareMode.LENIENT);
    }

    @Test
    public void givenJsonWithExtraFields_whenIgnoringExtraFields_thenEqual() throws JSONException {
        String jsonWithExtraFields = "["
                + "{\"id\": 1, \"name\": \"Alice\", \"address\": {\"city\": \"NY\", \"street\": \"5th Ave\"}, \"age\": 30}, "
                + "{\"id\": 2, \"name\": \"Bob\", \"address\": {\"city\": \"LA\", \"street\": \"Sunset Blvd\"}, \"age\": 25}"
                + "]";

        JSONAssert.assertEquals(jsonArray1, jsonWithExtraFields, JSONCompareMode.LENIENT);
    }

    @Test
    public void givenJsonCollection_whenIgnoringOrder_thenEqual() {
        assertThat(
                jsonArray1,
                sameJSONAs(jsonArray2)
                        .allowingAnyArrayOrdering());
    }

    @Test
    public void givenJsonWithUnexpectedFields_whenIgnoringUnexpectedFields_thenEqual() {
        String jsonWithUnexpectedFields = "["
                + "{\"id\": 1, \"name\": \"Alice\", \"address\": {\"city\": \"NY\", \"street\": \"5th Ave\"}, \"extraField\": \"ignoreMe\"}, "
                + "{\"id\": 2, \"name\": \"Bob\", \"address\": {\"city\": \"LA\", \"street\": \"Sunset Blvd\"}}"
                + "]";

        assertThat(
                jsonWithUnexpectedFields,
                sameJSONAs(jsonArray1)
                        .allowingExtraUnexpectedFields());
    }
}
