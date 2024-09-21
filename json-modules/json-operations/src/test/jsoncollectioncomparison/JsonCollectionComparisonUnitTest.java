package com.baeldung.jsoncollectioncomparison;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;

import static org.hamcrest.MatcherAssert.assertThat;
import static uk.co.datumedge.hamcrest.json.SameJSONAs.sameJSONAs;

public class JsonCollectionComparisonUnitTest {
    String jsonArray1 = "[{\"id\": 1, \"name\": \"Alice\"}, {\"id\": 2, \"name\": \"Bob\"}]";
    String jsonArray2 = "[{\"id\": 2, \"name\": \"Bob\"}, {\"id\": 1, \"name\": \"Alice\"}]";

    @Test
    public void givenJsonArrays_whenUsingJSONAssertIgnoringOrder_thenEqual() throws JSONException {
        JSONAssert.assertEquals(jsonArray1, jsonArray2, JSONCompareMode.LENIENT);
    }

    @Test
    public void givenJsonCollection_whenIgnoringOrder_thenEqual() {
        assertThat(
                jsonArray1,
                sameJSONAs(jsonArray2)
                        .allowingExtraUnexpectedFields()
                        .allowingAnyArrayOrdering());
    }
}
